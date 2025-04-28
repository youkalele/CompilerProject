(FUNCTION  test  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 1)]  [(r 3)])
    (OPER 5 Mov [(r 2)]  [(r 4)])
    (OPER 6 Mov [(r 2)]  [(r 5)])
    (OPER 7 Mov [(r 1)]  [(r 2)])
    (OPER 8 GT [(r 8)]  [(r 6)(r 7)])
    (OPER 9 BEQ []  [(r 8)(i 0)(bb lowlevel.BasicBlock@1e643faf)])
  )
  (BB 4
    (OPER 10 Sub_I [(r 11)]  [(r 9)(r 10)])
    (OPER 11 Mov [(r 2)]  [(r 11)])
  )
  (BB 5
    (OPER 29 EQ [(r 32)]  [(r 30)(r 31)])
    (OPER 30 BEQ []  [(r 32)(i 0)(bb lowlevel.BasicBlock@34ce8af7)])
  )
  (BB 14
    (OPER 31 EQ [(r 35)]  [(r 33)(r 34)])
    (OPER 32 BEQ []  [(r 35)(i 0)(bb lowlevel.BasicBlock@b684286)])
  )
  (BB 17
    (OPER 33 EQ [(r 38)]  [(r 36)(r 37)])
    (OPER 34 BEQ []  [(r 38)(i 0)(bb lowlevel.BasicBlock@880ec60)])
    (OPER 38 BNE []  [(r 35)(i 0)(bb lowlevel.BasicBlock@3f3afe78)])
  )
  (BB 19
    (OPER 35 Sub_I [(r 41)]  [(r 39)(r 40)])
    (OPER 36 Mov [(r 1)]  [(r 41)])
    (OPER 37 BNE []  [(r 38)(i 0)(bb lowlevel.BasicBlock@7f63425a)])
  )
  (BB 20
  )
  (BB 18
  )
  (BB 15
    (OPER 39 Mov [(r 2)]  [(r 42)])
    (OPER 40 Jmp []  [(bb lowlevel.BasicBlock@36d64342)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 11
    (OPER 20 Mov [(r 2)]  [(r 23)])
    (OPER 21 EQ [(r 26)]  [(r 24)(r 25)])
    (OPER 22 BEQ []  [(r 26)(i 0)(bb lowlevel.BasicBlock@39ba5a14)])
    (OPER 26 Jmp []  [(bb lowlevel.BasicBlock@511baa65)])
  )
  (BB 12
    (OPER 23 Add_I [(r 29)]  [(r 27)(r 28)])
    (OPER 24 Mov [(r 2)]  [(r 29)])
    (OPER 25 BNE []  [(r 26)(i 0)(bb lowlevel.BasicBlock@340f438e)])
  )
  (BB 13
  )
  (BB 6
    (OPER 12 Mov [(r 2)]  [(r 12)])
    (OPER 13 EQ [(r 15)]  [(r 13)(r 14)])
    (OPER 14 BEQ []  [(r 15)(i 0)(bb lowlevel.BasicBlock@30c7da1e)])
    (OPER 28 Jmp []  [(bb lowlevel.BasicBlock@5b464ce8)])
  )
  (BB 7
    (OPER 15 Mov [(r 2)]  [(r 16)])
    (OPER 16 EQ [(r 19)]  [(r 17)(r 18)])
    (OPER 17 BEQ []  [(r 19)(i 0)(bb lowlevel.BasicBlock@57829d67)])
    (OPER 27 BNE []  [(r 15)(i 0)(bb lowlevel.BasicBlock@19dfb72a)])
  )
  (BB 9
    (OPER 18 Add_I [(r 22)]  [(r 20)(r 21)])
    (OPER 19 Mov [(r 2)]  [(r 22)])
  )
  (BB 10
  )
  (BB 8
  )
)
