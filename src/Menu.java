import java.util.Scanner;
import java.sql.*;

public class Menu {

    private int opcao = 0;

    Scanner input = new Scanner(System.in);
    CadastraHospede hospede = new CadastraHospede();
    Checkin checkin = new Checkin();
    Servicos servico = new Servicos();
    Checkout checkout= new Checkout();

    public void displayMenu(Connection con){
        while(opcao != -1){
            System.out.println("Em qualquer ocasião caso queira voltar, digite -1");
            System.out.println("Neste menu, voltar encerra o programa");
            System.out.println("Escolha a opcao:");
            System.out.println("1. Cadastrar hóspede");
            System.out.println("2. Fazer check-in");
            System.out.println("3. Consumir serviço");
            System.out.println("4. Fazer checkout");
            System.out.printf("Escolha a opcao: ");
            opcao = input.nextInt();
            input.nextLine();
            switch(opcao){
                case 1:
                    hospede.cadastraHospede(con);
                    break;
                case 2:
                    checkin.checkin(con);
                    break;
                case 3:
                    servico.consome(con);
                    break;
                case 4:
                    checkout.checkout(con);
            }

        }
    }

}
