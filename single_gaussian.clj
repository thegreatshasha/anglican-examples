(ns hello-world
(:require [anglican importance lmh]
[gorilla-plot.core :as plot]
[anglican.stat :as stat])
(:use [anglican core runtime emit
[inference :only [infer collect-by equalize]]
[state :only [get-predicts get-log-weight set-log-weight]]]))

(defquery many-flips [outcomes]
(let [theta (sample (normal 1 100))
outcome-dist (normal theta 1)
likelihood (fn [x] (observe outcome-dist x))]
(map likelihood outcomes)
(predict :theta theta)))
(def many-flip-posterior (conditional many-flips :lmh))

(plot/histogram
(->> (repeatedly 1000 #(sample (normal 1 100)))
(map (fn [x] x))
)
:bins 20 :normalize :probability)

(plot/histogram
(->> (repeatedly 1000 #(sample (many-flip-posterior (repeat 100 100))))
(map (fn [x] (:theta x)))
)
:bins 20 :normalize :probability)
