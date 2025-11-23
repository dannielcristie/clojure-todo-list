(ns todo.backend.core
  (:require [ring.adapter.jetty :as jetty]
            [reitit.ring :as ring]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.cors :refer [wrap-cors]]
            [todo.backend.handler :as handler]
            [todo.backend.db :as db])
  (:gen-class))

;; --- 1. Definição das Rotas ---
(def app-routes
  (ring/router
   ["/api"
    ["/hello" {:get {:handler handler/hello-handler}}]

    ["/todos"
     {:get {:handler handler/list-todos-handler}
      :post {:handler handler/create-todo-handler}}]]))

;; --- 2. Definição da Aplicação (App) ---
(def app
  (ring/ring-handler
   app-routes
   (ring/create-default-handler)
   {:middleware [[wrap-cors :access-control-allow-origin [#"http://localhost:8000"]
                             :access-control-allow-methods [:get :post :put :delete]]
                 wrap-json-response
                 [wrap-json-body {:keywords? true}]
                 wrap-keyword-params
                 wrap-params]}))

;; --- 3. Função para Iniciar o Servidor ---
(defn start-server [port]
  (println (str "Servidor iniciado na porta " port))
  (jetty/run-jetty #'app {:port port :join? false}))

;; --- 4. Ponto de Entrada Principal (-main) ---
(defn -main [& args]
  (let [port (Integer/parseInt (or (first args) "3000"))]
    (db/initialize-database!)
    (start-server port)))