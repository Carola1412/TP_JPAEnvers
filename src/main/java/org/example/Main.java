package org.example;
import Entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("en marcha Caro");

        try {
            //Creando la factura
            entityManager.getTransaction().begin();


            Factura factura1= Factura.builder()
                    .build();
            factura1.setNumero(12);
            factura1.setFecha("10/08/2020");

            Domicilio dom= Domicilio.builder()
                    .nombreCalle("Palma")
                    .numero(581)
                    .build();
            Cliente cliente= Cliente.builder()
                    .id(1L)
                    .nombre("Nicolas")
                    .apellido("Martinez")
                    .dni(40221477)
                    .build();
            cliente.setDomicilio(dom);
            dom.setCliente(cliente);

            factura1.setCliente(cliente);

            //creamos las categorias
            Categoria perecederos = Categoria.builder()
                    .denominacion("Perecederos")
                    .build();
            Categoria lacteos = Categoria.builder()
                    .denominacion("lacteos")
                    .build();
            Categoria limpieza = Categoria.builder()
                    .denominacion("Limpieza")
                    .build();

            Articulo art1= Articulo.builder()
                    .cantidad(200)
                    .denominacion("Yought Ser sabor Frutilla")
                    .precio(20)
                    .build();
            Articulo art2= Articulo.builder()
                    .cantidad(300)
                    .denominacion("Detergente Magistral")
                    .precio(80)
                    .build() ;

            art1.getCategorias().add(perecederos);
            art1.getCategorias().add(lacteos);
            lacteos.getArticulos().add(art1);
            perecederos.getArticulos().add(art1);

            art2.getCategorias().add(limpieza);
            limpieza.getArticulos().add(art2);

            DetalleFactura det1 = DetalleFactura.builder()
                    .build();
            det1.setArticulo(art1);
            det1.setCantidad(2);
            det1.setSubtotal(40);

            art1.getDetalles().add(det1);
            factura1.getDetalles().add(det1);
            det1.setFactura(factura1);

            DetalleFactura det2= DetalleFactura.builder()
                    .build();
            det2.setArticulo(art2);
            det1.setCantidad(1);
            det2.setSubtotal(80);

            art2.getDetalles().add(det2);
            factura1.getDetalles().add(det2);
            det2.setFactura(factura1);

            factura1.setTotal(120);
            entityManager.persist(factura1);
            entityManager.getTransaction().commit();

            //Actulizando la factura
            entityManager.getTransaction().begin();

            factura1 =entityManager.find(Factura.class, 1L);
            factura1.setNumero(85);


            entityManager.merge(factura1);
            entityManager.getTransaction().commit();
            System.out.println("Factura actualizada "+factura1);

            //eliminando la facutra
            entityManager.getTransaction().begin();

            factura1 =entityManager.find(Factura.class, 1L);
            factura1.setNumero(85);

            entityManager.remove(factura1);
            entityManager.getTransaction().commit();
            System.out.println("Factura borrada "+factura1);

        } catch (Exception e) {

            entityManager.getTransaction().rollback();

            // Cerrar el EntityManager y el EntityManagerFactory
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}


