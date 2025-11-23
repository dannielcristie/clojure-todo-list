(ns todo.frontend.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdom]
            [clojure.string :as str]
            [cljs.core.async :refer [go]]
            [cljs.core.async.interop :refer-macros [<p!]]))

(defonce app-state (r/atom {:next-id 1
                            :input-text ""
                            :editing-id nil
                            :editing-text ""
                            :error-message nil
                            :todos []}))

(def api-url "http://localhost:3000/api")

(defn fetch-json [url options]
  (-> (js/fetch url (clj->js options))
      (.then (fn [response]
               (when-not (.-ok response)
                 (throw (js/Error. (str "HTTP error: " (.-status response)))))
               (.json response)))
      (.then #(js->clj % :keywordize-keys true))))

(defn get-todos []
  (swap! app-state assoc :loading true :error nil)
  (go
    (try
      (let [response (<p! (fetch-json (str api-url "/todos") {:method "GET"}))]
        (swap! app-state assoc :todos (:todos response) :loading false))
      (catch js/Error e
        (swap! app-state assoc :error (.-message e) :loading false)))))

(defn create-todo [todo-data]
  (let [title (:title todo-data)]
    (if (str/blank? title)
      (swap! app-state assoc :error-message "O título da tarefa não pode ser vazio.")
      (do
        (swap! app-state assoc :loading true :error-message nil)
        (go
          (try
            (<p! (fetch-json (str api-url "/todos")
                             {:method "POST"
                              :headers {"Content-Type" "application/json"}
                              :body (js/JSON.stringify (clj->js todo-data))}))
            (get-todos)
            (catch js/Error e
              (swap! app-state assoc :error (.-message e) :loading false))))))))

(defn toggle-todo
  "Chama a API para alternar o status de um todo."
  [id]
  (go
    (try
      (<p! (fetch-json (str api-url "/todos/" id "/toggle")
                       {:method "POST"}))
      (get-todos)
      (catch js/Error e
        (swap! app-state assoc :error (.-message e) :loading false)))))

(defn delete-todo
  "Chama a API para deletar um todo."
  [id]
  (go
    (try
      (<p! (js/fetch (str api-url "/todos/" id) (clj->js {:method "DELETE"})))
      (get-todos)
      (catch js/Error e
        (swap! app-state assoc :error (.-message e) :loading false)))))

(defn update-todo
  "Chama a API para atualizar um todo."
  [id updated-data]
  (swap! app-state assoc :loading true :error nil)
  (go
    (try
      (<p! (fetch-json (str api-url "/todos/" id)
                       {:method "PUT"
                        :headers {"Content-Type" "application/json"}
                        :body (js/JSON.stringify (clj->js updated-data))}))
      (get-todos)
      (catch js/Error e
        (swap! app-state assoc :error (.-message e) :loading false)))))

(defn todo-form []
  [:div.todo-input-container
   [:div.todo-input
    [:input
     {:type "text"
      :placeholder "O que precisa ser feito?"
      :value (:input-text @app-state)
      :on-change #(swap! app-state assoc :input-text (-> % .-target .-value) :error-message nil)}]
    
    [:button
     {:on-click (fn []
                  (create-todo {:title (:input-text @app-state)})
                  (swap! app-state assoc :input-text ""))}
     "Adicionar"]]
   
   (when (:error-message @app-state)
     [:span.error-message (:error-message @app-state)])])

(defn todo-item-component [todo]
  (let [editing? (= (:todos/id todo) (:editing-id @app-state))]
    [:li.todo-item {:class (when (= 1 (:todos/completed todo)) "completed")}
     
     [:input.todo-checkbox
      {:type "checkbox"
       :checked (not= 0 (:todos/completed todo))
       :on-change #(toggle-todo (:todos/id todo))}]
     
     (if editing?
       [:input.edit-input
        {:type "text"
         :value (:editing-text @app-state)
         :on-change #(swap! app-state assoc :editing-text (-> % .-target .-value))
         :on-blur (fn []
                    (update-todo (:todos/id todo) {:title (:editing-text @app-state)
                                                   :completed (not= 0 (:todos/completed todo))})
                    (swap! app-state assoc :editing-id nil :editing-text ""))
         :on-key-down (fn [e]
                        (when (= (.-keyCode e) 13) ; Enter key
                          (.blur (.-target e))))}]
       [:span.todo-title {:on-click #(swap! app-state assoc :editing-id (:todos/id todo) :editing-text (:todos/title todo))}
        (:todos/title todo)])
     
     [:button.delete-btn {:on-click #(delete-todo (:todos/id todo))} "X"]]))

(defn todo-list []
  [:ul.todo-list
   (for [todo (:todos @app-state)]
     ^{:key (:todos/id todo)}
     [todo-item-component todo])])

(defn app []
  [:div.todo-app
   [:h1 "Todo App"]
   [todo-form]
   [todo-list]])

(defn ^:export init []
  (let [root (rdom/create-root (js/document.getElementById "app"))]
    (.render root (r/as-element [app])))
  (get-todos))