
package parser;

import compiler.CMinusCompiler;
import lowlevel.CodeItem;
import lowlevel.Data;
import scanner.Token;

public class VarDecl extends Declaration{
    private int arrVal=-1;

    public VarDecl(Token idToken, int val)
    {
        id = idToken;
        arrVal = val;
    }

    public String printDecl(String tabs) {
        String declString="";
        declString+=(tabs + "int ");
        declString+=super.printDecl(tabs);
        if(arrVal!=-1)
            declString+=("[" + arrVal + "]\n");
        return declString;
    }

    //should have 2 versions
    public CodeItem genLLCode() {
        Data decl;
        if(arrVal==-1)
            decl = new Data(Data.TYPE_INT, id.getData());
        else
            decl = new Data(Data.TYPE_INT, id.getData(), true, arrVal);
        
        CMinusCompiler.globalHash.put(decl.getName().hashCode(), decl.getName());
        
        return decl;
        
        //generate new Data
        //link to other CodeItems in list
    }
}
