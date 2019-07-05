import java.util.Scanner;
import java.util.ArrayList;
import java.sql.*;
import java.time.*;
import java.sql.Date;


public class Checkin {
    private int pk_quarto;
    public int pk_cliente;
    private int opcao = 0;
    public int busca_cpf;
    public String busca_nome;
    private Scanner input = new Scanner(System.in);

    public void checkin(Connection con){
        do {
            System.out.println("Selecione o hóspede:");
            System.out.println("1. Busca por nome");
            System.out.println("2. Busca por CPF");
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao){
                case 1:
                    System.out.println("Insira o nome que deseja buscar: ");
                    this.busca_nome = input.nextLine();
                    if (busca_nome.equals("-1")){
                        break;
                    }
                    if (!this.consultaDadosNome(con)){
                        break;
                    }
                    if (!this.consultaQuartosDisponiveis(con)){
                        break;
                    }
                    this.alugaQuarto(con);
                    return;
                case 2:
                    System.out.println("Insira o CPF que deseja buscar: ");
                    this.busca_cpf = input.nextInt();
                    if (busca_cpf == -1){
                        break;
                    }
                    if (!this.consultaDadosCPF(con)){
                        break;
                    }
                    break;
            }
        } while(opcao != -1);
    }

    public boolean consultaDadosNome(Connection con){
        try {
            Statement stat = con.createStatement();
            String sql = "select * " +
                    "from hospede " +
                    "where hospede_nome like '%" + this.busca_nome + "%'";
            ResultSet rs = stat.executeQuery(sql);


            if (rs.next() == false) {
                System.out.println("Registro não encontrado!");
                return false;
            }
            else {
                String atributos = String.format("   %-50s %-50s %-15s %-15s", "Nome", "Endereço", "CPF", "Telefone");
                System.out.println(atributos);
                int i = 0;
                ArrayList<Integer> list = new ArrayList<>();
                do {
                    String hospede_nome = rs.getString("hospede_nome");
                    String hospede_endereco = rs.getString("hospede_endereco");
                    int hospede_telefone = rs.getInt("telefone");
                    int hospede_cpf = rs.getInt("hospede_cpf");
                    System.out.println(String.format("%d. %-50s %-50s %-15d %-15d", i + 1, hospede_nome, hospede_endereco, hospede_cpf, hospede_telefone));
                    list.add(rs.getInt("id_hospede"));
                    i++;
                } while (rs.next());
                i--;
                System.out.println("\n\n");
                System.out.println("Selecione o cliente: ");
                int no_hospede;
                no_hospede = input.nextInt();
                input.nextLine();
                if (no_hospede == -1){
                    return false;
                }
                while (no_hospede < 1 || no_hospede > i + 1){
                    System.out.println("Escolha um hospede entre 1 e " + (i + 1));
                    no_hospede = input.nextInt();
                    input.nextLine();
                    if (no_hospede == -1){
                        return false;
                    }
                }
                this.pk_cliente = list.get(no_hospede - 1);
            }
                return true;

        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    public boolean consultaDadosCPF(Connection con){
        try {
            Statement stat = con.createStatement();
            String sql = "select * " +
                    "from hospede " +
                    "where hospede_cpf = " + this.busca_cpf;
            ResultSet rs = stat.executeQuery(sql);


            if (rs.next() == false) {
                System.out.println("Registro não encontrado!");
                return false;
            }
            else {
                String atributos = String.format("   %-50s %-50s %-15s %-15s", "Nome", "Endereço", "CPF", "Telefone");
                System.out.println(atributos);
                int i = 0;
                ArrayList<Integer> list = new ArrayList<>();
                do {
                    String hospede_nome = rs.getString("hospede_nome");
                    String hospede_endereco = rs.getString("hospede_endereco");
                    int hospede_telefone = rs.getInt("telefone");
                    int hospede_cpf = rs.getInt("hospede_cpf");
                    System.out.println(String.format("%d. %-50s %-50s %-15d %-15d", i + 1, hospede_nome, hospede_endereco, hospede_cpf, hospede_telefone));
                    list.add(rs.getInt("id_hospede"));
                    i++;
                } while (rs.next());
                i--;
                System.out.println("\n\n");
                System.out.println("Selecione o cliente: ");
                int no_hospede;
                no_hospede = input.nextInt();
                input.nextLine();
                if (no_hospede == -1){
                    return false;
                }
                while (no_hospede < 1 || no_hospede > i + 1){
                    System.out.println("Hospede inexistente");
                    no_hospede = input.nextInt();
                    input.nextLine();
                    if (no_hospede == -1){
                        return false;
                    }
                }
                this.pk_cliente = list.get(no_hospede - 1);
            }
            return true;
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    public boolean consultaQuartosDisponiveis(Connection con){
        try {
            Statement stat = con.createStatement();
            String sql = "select * " +
                    "from quarto " +
                    "where hospede_quarto = 0";
            ResultSet rs = stat.executeQuery(sql);

            if (rs.next() == false) {
                System.out.println("Sem quartos disponíveis");
                return false;
            }
            else {
                System.out.println("QUARTOS DISPONÍVEIS");
                String atributos = String.format("   %-15s %-17s %-20s", "No Quarto", "Preço Quarto", "Tipo Quarto");
                System.out.println(atributos);
                int i = 0;
                ArrayList<Integer> list = new ArrayList<>();
                do {
                    int no_quarto = rs.getInt("no_quarto");
                    float preco_quarto = rs.getFloat("preco_quarto");
                    String tipo_quarto = rs.getString("tipo_quarto");
                    System.out.println(String.format("%d. %-15d %-17.2f %-20s", i + 1, no_quarto, preco_quarto, tipo_quarto));
                    list.add(rs.getInt("id_quarto"));
                    i++;
                } while (rs.next());
                i--;
                System.out.println("\n\n");
                System.out.println("Selecione o quarto: ");
                int num_quarto;
                num_quarto = input.nextInt();
                input.nextLine();
                if (num_quarto == -1){
                    return false;
                }
                while (num_quarto < 1 || num_quarto > i + 1){
                    System.out.println("O quarto escolhido nao esta disponivel, tente novamente");
                    num_quarto = input.nextInt();
                    input.nextLine();
                    if (num_quarto == -1){
                        return false;
                    }
                }
                this.pk_quarto = list.get(num_quarto - 1);
            }
            return true;
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    public void alugaQuarto(Connection con){
        try {
            String data;
            Statement stat = con.createStatement();
            String sql = "select checkin " +
                    "from hospede " +
                    "where id_hospede = " + this.pk_cliente;
            ResultSet rs = stat.executeQuery(sql);
            rs.next();
            data = rs.getString("checkin");
            if (!data.equals("1111-11-11")){
                System.out.println("O cliente ja esta hospedado");
                return;
            }
            sql = "update quarto " +
                    "set hospede_quarto = " + this.pk_cliente + " " +
                    "where id_quarto = " + this.pk_quarto;
            stat.executeUpdate(sql);
            LocalDate today = LocalDate.now();
            sql = "update hospede " +
                    "set checkin = '" + today.toString() + "' " +
                    "where id_hospede = " + this.pk_cliente;
            stat.executeUpdate(sql);
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

}
