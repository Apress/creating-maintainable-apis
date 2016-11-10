#lang scheme

(require "type-tagging.rkt")
(require "function-composition.rkt")

(provide coerce-args)

; The levels are: integer (0), rational (1), real (2) and complex (3).
(define (type-level x) 
  (let ((type-of-x (type-tag x)))
    (cond ((eq? type-of-x 'complex) 3)
          ((eq? type-of-x 'rational) 1)
          (else
           (if (exact-integer? (contents x)) 0 2)))))

; The 'raise' is a function raising its input argument into a higher level type.
; Each line in the implementation is mapped to the corresponding use case step.
; Step 1.
(define (coerce-args raise args)
  ; Step 2.
  (let ((target-level (foldr max 0 (map type-level args))))
    ; Steps 3-4.
    (map (lambda (x) ((repeated raise (- target-level (type-level x))) x))
         args)))
