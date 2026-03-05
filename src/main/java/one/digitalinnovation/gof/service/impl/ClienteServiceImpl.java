package one.digitalinnovation.gof.service.impl;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*Implementação da Strategy {@Link ClienteService}, a qual pode ser injetada pelo spring
* (Via {@Link Autowired}. Com isso, como essa classe é um {@Link Service}, ela será tratada
* como um Singleton*/
@Service
public class ClienteServiceImpl implements ClienteService {

    // Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    // Strategy: Implementar os métodos definidos na interface.
    // Facade: Abstrair integraçõescom subsistemas, provendo uma interface simples.
    @Override
    public Iterable<Cliente> buscarTodos() {
    // Buscar todos clientes.

        return clienteRepository.findAll();
    }
    @Override
    public Cliente buscarPorId(Long id) {
        // Buscar Cliente por id.
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }
    @Override
    public void inserir(Cliente cliente){
       salvarClienteComCep(cliente);
    }
    @Override
    public void atualizar(Long id, Cliente cliente) {
    // Buscar Cliente por ID, caso exista.
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()){
            salvarClienteComCep(cliente);
        }
        // Verificar se o endereço do Cliente já existe (pelo CEP).
        // Caso não exista, integrar com ViaCep e persistir o retorno.
        // Alterar Cliente, vinculando o Endereço (Novo ou existente).

    }
    private void salvarClienteComCep(Cliente cliente) {
        //Verificar se o endereço do clientejá existe (pelo CEP).
        String cep = cliente.getEndereco().getCep();
        //Caso não exista integrar com o ViaCEP e persistir o retorno.
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        //Inserir Cliente, vinculando o endereço (Novo ou Existente).
        clienteRepository.save(cliente);
    }
@Override
    public void deletar(Long id) {
    clienteRepository.deleteById(id);
    }
}
