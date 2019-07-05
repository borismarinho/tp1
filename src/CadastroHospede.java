import java.util.Scanner;
import java.sql.*;

public class CadastraHospede {
    private String nome;
    private String endereco;
    private int cpf;
    private int telefone;
    private Scanner input = new Scanner(System.in);
    private boolean flag = true;

    public void cadastraHospede(Connection con){

        System.out.println("Informe o nome do hospede: ");
        this.setNome(input.nextLine());
        if (nome.equals("-1")){
            return;
        }

        System.out.println("Informe o endereco do hospede: ");
        this.setEndereco(input.nextLine());
        if (endereco.equals("-1")){
            return;
        }

        System.out.println("Informe o telefone do hospede: ");
        this.setTelefone(input.nextInt());
        input.nextLine();
        if (telefone == -1){
            return;
        }

        do {
            System.out.println("Informe o cpf do hospede: ");
            this.setCpf(input.nextInt());
            input.nextLine();
            if (cpf == -1){
                return;
            }
            flag = this.checaCPF(con);
        } while (flag);
        this.incluiClienteBD(con);
        System.out.println(getNome());
        System.out.println(getEndereco());
        System.out.println(getTelefone());
        System.out.println(getCpf());
    }

    public boolean checaCPF(Connection con){
        try {
            Statement stat = con.createStatement();
            String sql = "select hospede_nome " +
                    "from hospede " +
                    "where hospede_cpf = " + cpf;
            ResultSet rs = stat.executeQuery(sql);

            if (rs.next() == false) {
                return false;
            } else {
                String hospede_nome = rs.getString("hospede_nome");
                System.out.println("CPF já cadastrado!");
                System.out.println("Nome: " + hospede_nome);
                return true;
            }
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
            return true;
        }
    }

    public void incluiClienteBD(Connection con){
        try {
            Statement stat = con.createStatement();
            String sql = "insert into hospede " +
                    "(hospede_nome, hospede_endereco, hospede_cpf, telefone, checkin, checkout) " +
                    "values ('" + this.nome + "', '" + this.endereco + "', " + this.cpf + ", " + this.telefone + ", '1111-11-11', '1111-11-11')";
                    stat.executeUpdate(sql);
            //            ResultSet rs = stat.executeQuery(sql);
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
}
