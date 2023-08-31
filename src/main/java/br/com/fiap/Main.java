package br.com.fiap;

import br.com.fiap.domain.entity.Bem;
import br.com.fiap.domain.entity.Departamento;
import br.com.fiap.domain.entity.Inventario;
import br.com.fiap.domain.entity.TipoDeBem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        TipoDeBem tipoBem = new TipoDeBem();
        tipoBem.setNome("Veículo");

        Departamento departamento = new Departamento();
        departamento.setNome("Departamento Benezinho");

        Bem bem = new Bem();
        bem.setAquisicao(LocalDate.now()).setEtiqueta("M").setTipo(tipoBem).setNome("HB20").setLocalizacao(departamento);

        Inventario inventario = new Inventario();
        inventario.setDepartamento(departamento).setInicio(LocalDate.now()).setRelatorio("Relatório teste");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager manager = factory.createEntityManager();

        manager.getTransaction().begin();

        manager.persist(tipoBem);
        manager.persist(departamento);
        manager.persist(bem);
        manager.persist(inventario);

        manager.getTransaction().commit();

        findById( manager );

        List<Bem> list = manager.createQuery( "SELECT b FROM Bem b" ).getResultList();
        list.forEach( System.out::println );

        System.out.println(list);
        System.out.println(inventario);

        manager.close();
        factory.close();
    }

    private static void findById(EntityManager manager) {
        Long id = Long.valueOf( JOptionPane.showInputDialog( "Informe o ID do Inventario: " ) );
        Inventario inventario = manager.find( Inventario.class, id );
        System.out.println( inventario );
    }
}