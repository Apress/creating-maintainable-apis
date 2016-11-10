#lang scheme

(provide identity)
(provide compose)
(provide repeated)

(define (identity x) x)

(define (compose f g)
  (lambda (x) (f (g x))))

(define (repeated f n)
  (cond ((= n 0) identity)
        ((= n 1) f)
        (else (compose f (repeated f (- n 1))))))


; Couple of test cases.
; We define here our auxiliary increment and decrement functions.
(define (inc x) (+ x 1))
(define (dec x) (- x 1))

(inc 1)
; The output is 1.

((identity inc) 1)
; The output is also 1.

((compose inc dec) 1)
; The output is 1, since we are applying the composition (inc(dec(x)) to x=1.

((repeated inc 5) 1)
; The output is 6, since we are applying the composition (inc(inc(inc(inc(inc(x)))))) to x=1.