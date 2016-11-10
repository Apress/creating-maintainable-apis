#lang scheme

(provide attach-tag)
(provide type-tag)
(provide contents)

(define (attach-tag type-tag contents)
  (if (eq? type-tag 'scheme-number)
      contents
      (cons type-tag contents)))
  
  (define (type-tag datum)
    (cond ((pair? datum) (car datum))
          ((number? datum) 'scheme-number)
          (error "Bad tagged datum -- TYPE-TAG" datum)))
  
  (define (contents datum)
    (cond ((pair? datum) (cdr datum))
          ((number? datum) datum)
          (error "Bad tagged datum -- CONTENTS" datum)))