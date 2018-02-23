;  Difficulty: trivial
;  https://www.hackerrank.com/challenges/camelcase/problem
(println (+' 1 (count (filter #(Character/isUpperCase %) (read-line)))))
