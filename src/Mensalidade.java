import java.util.Calendar;

public class Mensalidade {
    private int id;
    private int mes;
    private String status;
    private double valor; //vai depender da quantidade de dias
    private Calendar data_pg;
    private String Modalidade;

    //para o valor é interessante perguntar os dias e variar o preço
    public Mensalidade(double valor, String modalidade) {
        Calendar c = Calendar.getInstance();
        this.mes = c.get(Calendar.MONTH) + 1;// pega o número do mês mas começa em 0
        c.add(Calendar.MONTH, 1);
        this.valor = valor;
        this.status = "Ativo";
        this.data_pg = c;
        this.Modalidade = modalidade;
    }
    public Mensalidade(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModalidade() {
        return Modalidade;
    }

    public void setModalidade(String modalidade) {
        Modalidade = modalidade;
    }

    public Calendar getData_pg() {
        return data_pg;
    }

    public void setData_pg(Calendar data_pg) {
        this.data_pg = data_pg;
    }


}
