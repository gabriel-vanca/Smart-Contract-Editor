package pipe.ucl.models;

public class InputLine {
    private String type;
    private String[] parameterList;

//    public InputLine() {
//        parameterList = new String[];
//    }
//
//    public InputLine(String _type) {
//        parameterList = new String[];
//        this.type = _type;
//    }

    public InputLine(String _type, String[] _parameterList) {
        parameterList = _parameterList;
        this.type = _type;
    }

    public String[] getParameterList() {
        return parameterList;
    }

    public String getType() {
        return type;
    }
}
