#lang scheme

(require "function-composition.rkt")

(provide zero)
(provide one)
(provide two)
(provide inc)
(provide sum)
(provide encode)

(define zero
  (lambda (f) identity))
(define one
  (lambda (f) (lambda (m) (f m))))
(define two
  (lambda (f) (compose f f)))
(define (inc n)
  (lambda (f) (compose f (n f))))
(define (sum n m)
  (lambda (f) (compose (n f) (m f))))

; SOLUTION TO EXERCISE
(define (encode i)
  ((repeated inc i) zero))


; Some test cases to make this more intuitive.
; This is an auxiliary function to be used as input for numerals.
(define (dec x) (- x 1))

((zero dec) 1)
; This is 1, as we have applied zero times the dec function on 1.
((one dec) 1)
; This is 0, as we have once applied the dec function on 1.
((two dec) 1)
; This is -1, as we have twice applied the dec function on 1.
(((inc two) dec) 1)
; This is -2, as we have applied three times (one more than two) the dec function on 1.
(((sum one two) dec) 1)
; This is -2, as we have applied three times (one + two) the dec function on 1.

; TEST CASE FOR EXERCISE
(((encode 4) dec) 1)
; This is -3, as we have applied four times the dec function on 1.