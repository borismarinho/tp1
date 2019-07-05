import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public class Checkout {

    Scanner input = new Scanner(System.in);
    Checkin info = new Checkin();

    public void checkout(Connection con) {
        System.out.println("Até logo! Até mais ver! Bon voyage! Arrivederci! Até mais! Adeus! Boa viagem! Vá em paz! " +
                "Que a po...");
        System.out.println("\n\n\nIniciar checkout");
        int opcao;
        Checkin checaDados = new Checkin();
        do {
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
                    geraFatura(checaDados.pk_cliente, con);
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

    public void geraFatura(int pk_cliente, Connection con) {
        float total_consumo = 0;
        long total_dias = 0;
        float preco_estadia;
        try {
            Statement stat = con.createStatement();
            String sql;
            sql = "select * " +
                    "from quarto " +
                    "where hospede_quarto = " + pk_cliente;
            ResultSet rs = stat.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("O cliente nao esta hospedado no hotel!");
                return;
            }
///////////////////////////////////////////////////////////
            sql = "select * " +
                    "from servico " +
                    "where hospede_servico = " + pk_cliente;
            rs = stat.executeQuery(sql);
            if (!rs.next()){
                total_consumo = 0;
            }
            else {
                    System.out.println(String.format("%-20s %-10s", "Serviço", "Preço"));
                do {
                    String tipo_servico = rs.getString("tipo_servico");
                    float preco_servico = rs.getFloat("preco_servico");
                    total_consumo += preco_servico;
                    System.out.println(String.format("%-20s %-10.2f", tipo_servico, preco_servico));
                } while(rs.next());
            }
///////////////////////////////////////////////////////////
            sql = "select * " +
                    "from quarto " +
                    "where hospede_quarto = " + pk_cliente;
            rs = stat.executeQuery(sql);
            rs.next();
            float preco_quarto = rs.getFloat("preco_quarto");
            String tipo_quarto = rs.getString("tipo_quarto");
            int no_quarto = rs.getInt("no_quarto");
///////////////////////////////////////////////////////////
            sql = "select * " +
                    "from hospede " +
                    "where id_hospede = " + pk_cliente;
            rs = stat.executeQuery(sql);
            rs.next();
            String checkin = rs.getString("checkin");
///////////////////////////////////////////////////////////
            String checkout;
            LocalDate today = LocalDate.now();
            checkout = today.toString();
            total_dias = calculaDias(checkin, checkout);
            preco_estadia = total_dias * preco_quarto;
            System.out.println(String.format("\n%-20s %-20s %-20s %-20s", "No. Quarto", "Tipo do Quarto", "Total Diárias", "Total Hospedagem"));
            System.out.println(String.format("%-20d %-20s %-20d %-20.2f", no_quarto, tipo_quarto, total_dias, preco_estadia));
            System.out.println("\nTotal a pagar: " + String.format("%.2f", preco_estadia + total_consumo));
///////////////////////////////////////////////////////////
            sql = "delete from servico " +
                    "where hospede_servico = " + pk_cliente;
            stat.executeUpdate(sql);
///////////////////////////////////////////////////////////
            sql = "update quarto "+
                    "set hospede_quarto = 0 " +
                    "where hospede_quarto = " + pk_cliente;
            stat.executeUpdate(sql);
///////////////////////////////////////////////////////////
            sql = "update hospede "+
                    "set checkin = '1111-11-11' " +
                    "where id_hospede = " + pk_cliente;
            stat.executeUpdate(sql);
///////////////////////////////////////////////////////////
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public long calculaDias(String checkin, String checkout){
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = myFormat.parse(checkin);
            Date date2 = myFormat.parse(checkout);
            long diff = date2.getTime() - date1.getTime();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
