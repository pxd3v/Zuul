
package zuul;

public class Item {
    private String descricao;
    private String action;

    public Item(String desc, String action) {
        this.descricao = desc;
        this.action = action;
    }

    public String doSomething() {
        return this.action;
    }
    
    public String looking() {
        return this.descricao;
    }
}
