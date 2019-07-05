import java.util.Scanner;
import java.sql.*;

public class Servicos {

    private int flag = 1;
    private int servico;


    private Scanner input = new Scanner(System.in);

    public void consome(Connection con){
        do {
            System.out.println("O que deseja consumir?");
            System.out.println("1. Restaurante");
            System.out.println("2. Lavanderia");
            System.out.println("3. Frigobar");
            System.out.println("-1. Sair");
            servico = input.nextInt();
            input.nextLine();
            if (servico == -1){
                flag = 0;
            }
            else {
                adicionaConta(servico, con);
            }
        } while (flag == 1);
    }

    public void adicionaConta(int servico, Connection con){
        int opcao;
        Checkin checaDados = new Checkin();
        do {
            System.out.println("CONSUMO DE SERVIÇOS");
            System.out.println("1. Buscar por nome");
            System.out.println("2. Buscar por CPF");
            opcao = input.nextInt();
            input.nextLine();
            if (opcao != 1 && opcao != 2){
                System.out.println("Escolha uma opcao das opcoes mostradas");
                System.out.println("Insira -1 caso deseje voltar");
            }
            if (opcao == -1){
                return;
            }
            switch (opcao){
                case 1:
                    System.out.println("Insira o nome que de seja buscar");
                    checaDados.busca_nome = input.nextLine();
                    if (checaDados.busca_nome.equals("-1")){
                        break;
                    }
                    checaDados.consultaDadosNome(con);
                    insereBD(1, con, checaDados.pk_cliente);
                    break;
                case 2:
                    System.out.println("Insira o CPF");
                    checaDados.busca_cpf = input.nextInt();
                    input.nextLine();
                    if (checaDados.busca_cpf == -1){
                        break;
                    }
                    checaDados.consultaDadosCPF(con);
                    break;
            }
        } while(opcao != -1);
    }

    public void insereBD(int servico, Connection con, int pk_cliente){
        try {
            Statement stat = con.createStatement();
            String sql;
            sql = "select * " +
                    "from quarto " +
                    "where hospede_quarto = " + pk_cliente;
            ResultSet rs = stat.executeQuery(sql);
            if (!rs.next()){
                System.out.println("O cliente nao esta hospedado no hotel!");
                return;
            }

            if (servico == 1){

                sql = "insert into servico " +
                "(tipo_servico, preco_servico, hospede_servico) " +
                "values ('Restaurante', 60.00, " + pk_cliente + ")";
                stat.executeUpdate(sql);
            }
            if (servico == 2){
                sql = "insert into servico " +
                    "(tipo_servico, preco_servico, hospede_servico) " +
                    "values ('Lavanderia', 20.00, " + pk_cliente + ")";
                    stat.executeUpdate(sql);
            }
            if (servico == 3){
                sql = "insert into servico " +
                        "(tipo_servico, preco_servico, hospede_servico) " +
                        "values ('Frigobar', 30.00, " + pk_cliente + ")";
                stat.executeUpdate(sql);
            }
            //            ResultSet rs = stat.executeQuery(sql);
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

}
