(FUNCTION  putDigit  [(int s)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Add_I [(r 4)]  [(r 2)(r 3)])
    (OPER 5 Pass []  [(r 4)] [(PARAM_NUM 0)])
    (OPER 6 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 7 Mov [(r 5)]  [(m RetReg)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  printInt  [(int x)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 LT [(r 6)]  [(r 4)(r 5)])
    (OPER 5 BEQ []  [(r 6)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 6 Pass []  [(r 7)] [(PARAM_NUM 0)])
    (OPER 7 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 8 Mov [(r 8)]  [(m RetReg)])
    (OPER 9 Sub_I [(r 11)]  [(r 9)(r 10)])
    (OPER 10 Mov [(r 1)]  [(r 11)])
  )
  (BB 5
    (OPER 11 Mov [(r 3)]  [(r 12)])
    (OPER 12 Div_I [(r 15)]  [(r 13)(r 14)])
    (OPER 13 Mov [(r 1)]  [(r 15)])
    (OPER 14 Mul_I [(r 19)]  [(r 17)(r 18)])
    (OPER 15 Sub_I [(r 20)]  [(r 16)(r 19)])
    (OPER 16 Mov [(r 2)]  [(r 20)])
    (OPER 17 GT [(r 23)]  [(r 21)(r 22)])
    (OPER 18 BEQ []  [(r 23)(i 0)(bb 8)])
  )
  (BB 7
    (OPER 19 Pass []  [(r 24)] [(PARAM_NUM 0)])
    (OPER 20 JSR []  [(s printInt)] [(numParams 1)])
    (OPER 21 Mov [(r 25)]  [(m RetReg)])
  )
  (BB 8
    (OPER 22 Pass []  [(r 26)] [(PARAM_NUM 0)])
    (OPER 23 JSR []  [(s putDigit)] [(numParams 1)])
    (OPER 24 Mov [(r 27)]  [(m RetReg)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  euler  [(int x) (int y)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 EQ [(r 5)]  [(r 3)(r 4)])
    (OPER 5 BEQ []  [(r 5)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 6 LT [(r 8)]  [(r 6)(r 7)])
    (OPER 7 BEQ []  [(r 8)(i 0)(bb 9)])
  )
  (BB 7
    (OPER 8 Sub_I [(r 11)]  [(r 9)(r 10)])
    (OPER 9 Mov [(m RetReg)]  [(r 11)])
    (OPER 10 Jmp []  [(bb 1)])
  )
  (BB 8
  )
  (BB 5
    (OPER 14 Div_I [(r 16)]  [(r 14)(r 15)])
    (OPER 15 Mul_I [(r 18)]  [(r 16)(r 17)])
    (OPER 16 Sub_I [(r 19)]  [(r 13)(r 18)])
    (OPER 17 Mov [(r 1)]  [(r 19)])
    (OPER 18 Pass []  [(r 20)] [(PARAM_NUM 0)])
    (OPER 19 Pass []  [(r 21)] [(PARAM_NUM 1)])
    (OPER 20 JSR []  [(s euler)] [(numParams 2)])
    (OPER 21 Mov [(r 22)]  [(m RetReg)])
    (OPER 22 Mov [(m RetReg)]  [(r 0)])
    (OPER 23 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 9
    (OPER 11 Mov [(m RetReg)]  [(r 12)])
    (OPER 12 Jmp []  [(bb 1)])
    (OPER 13 Jmp []  [(bb 8)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 1)]  [(r 3)])
    (OPER 5 Sub_I [(r 6)]  [(r 4)(r 5)])
    (OPER 6 Mov [(r 2)]  [(r 6)])
    (OPER 7 Pass []  [(r 7)] [(PARAM_NUM 0)])
    (OPER 8 Pass []  [(r 8)] [(PARAM_NUM 1)])
    (OPER 9 JSR []  [(s euler)] [(numParams 2)])
    (OPER 10 Mov [(r 9)]  [(m RetReg)])
    (OPER 11 Pass []  [(r 0)] [(PARAM_NUM 0)])
    (OPER 12 JSR []  [(s printInt)] [(numParams 1)])
    (OPER 13 Mov [(r 10)]  [(m RetReg)])
    (OPER 14 Pass []  [(r 11)] [(PARAM_NUM 0)])
    (OPER 15 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 16 Mov [(r 12)]  [(m RetReg)])
    (OPER 17 Mov [(m RetReg)]  [(r 13)])
    (OPER 18 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
