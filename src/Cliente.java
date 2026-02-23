public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String sexo;
    private String telefone;
    private Mensalidade mensalidade;

    public Cliente(String nome, String cpf, String sexo, String telefone, Mensalidade mensalidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.telefone = telefone;
        this.mensalidade = mensalidade;
    }

    public Cliente(){

    }

    public Integer getMensalidadeId() {
        return mensalidade != null ? mensalidade.getId() : null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Mensalidade getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(Mensalidade mensalidade) {
        this.mensalidade = mensalidade;
    }
}
