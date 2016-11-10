#lang scheme

(provide subsets)

(define (subsets s)
  (if (null? s)
      (list '())
      (let ((first-of-s (list (car s)))
            (smaller-subsets (subsets (cdr s))))
        (append smaller-subsets
                (map (lambda (t) (append first-of-s t))
                     smaller-subsets)))))


; Test case to demonstrate how this works.
(subsets '(1 2 3))
;(() (3) (2) (2 3) (1) (1 3) (1 2) (1 2 3))