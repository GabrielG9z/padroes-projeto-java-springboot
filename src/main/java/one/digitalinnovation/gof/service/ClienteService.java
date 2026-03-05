package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Cliente;
import org.springframework.stereotype.Service;

/*Interface que define o padrão <b>Strategy</b> no domínio de Cliente. Com isso, se necessário, podemos
* ter multiplas implementações dessa mesma interface.*/

public interface ClienteService {

        Iterable<Cliente> buscarTodos();

        Cliente buscarPorId(Long id);

        void inserir(Cliente cliente);

        void atualizar(Long id, Cliente cliente);

        void deletar(Long id);
    }

