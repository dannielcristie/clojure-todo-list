(ns todo.frontend.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdom]
            [clojure.string :as str]))

;; --- 1. Adicione este "Cérebro" Reativo ---
(defonce app-state (r/atom {:next-id 1
                            :input-text ""
                            :todos []}))

;; --- 2. Adicione esta Lógica de Negócios (Local) ---
(defn adicionar-todo-local []
  (swap! app-state
         (fn [estado-atual]
           (let [novo-titulo (:input-text estado-atual)
                 novo-id (:next-id estado-atual)]
             (if (str/blank? novo-titulo)
               estado-atual ;; Não faz nada se vazio
               ;; Retorna um NOVO estado
               {:next-id (inc novo-id)
                :input-text "" ;; Limpa o input
                :todos (conj (:todos estado-atual)
                             {:id novo-id
                              :title novo-titulo})}
               )))))

(defn todo-form []
  [:div.todo-input
   [:input
    {:type "text"
     :placeholder "O que precisa ser feito?"
     :value (:input-text @app-state)
     :on-change #(swap! app-state assoc :input-text (-> % .-target .-value))}]
   
   [:button
    {:on-click adicionar-todo-local}
    "Adicionar (Local)"]])

(defn todo-list []
  [:ul.todo-list
   (for [todo (:todos @app-state)]
     ^{:key (:id todo)}
     [:li.todo-item
      (:title todo)])])

(defn app []
  [:div.todo-app
   [:h1 "Todo App (Local)"]
   [todo-form]
   [todo-list]])

(defn ^:export init []
  (println "Frontend 'Todo Interativo' inicializado...")
  (let [root (rdom/create-root (js/document.getElementById "app"))]
    (.render root (r/as-element [app]))))