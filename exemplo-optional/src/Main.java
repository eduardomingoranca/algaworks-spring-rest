import com.algaworks.model.Caminhao;
import com.algaworks.model.Motorista;
import com.algaworks.model.Seguro;
import com.algaworks.repository.Motoristas;

public class Main {
    // testando a cobertura do seguro de um caminhao
    public static void main(String[] args) {
        Motoristas motoristas = new Motoristas();
//        Motorista motorista = motoristas.porNome("Jose");

//        String cobertura = motorista.getCaminhao().getSeguro().getCobertura();
//        String cobertura = "Sem seguro";
//
//        if (cobertura != null) {
//            Caminhao caminhao = motorista.getCaminhao();
//            if (caminhao != null) {
//                Seguro seguro = caminhao.getSeguro();
//                if (seguro != null) {
//                    cobertura = seguro.getCobertura();
//                }
//            }
//        }

//         map -> Optional<Optional<Object>>
//         flatMap -> Optional<Object>
        String cobertura = motoristas.porNome("Juan")
                .flatMap(Motorista::getCaminhao)
                .flatMap(Caminhao::getSeguro)
                .map(Seguro::getCobertura)
                .orElse("Sem cobertura");

        System.out.println("A cobertura eh: " + cobertura);

    }
}