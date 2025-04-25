package com.dam.adp.fitness360proyecto3eval.baseDatos;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Clase utilitaria para la serialización y deserialización de objetos a/desde XML.
 * Utiliza JAXB (Java Architecture for XML Binding) para realizar las operaciones
 * de marshalling (objeto a XML) y unmarshalling (XML a objeto).
 * Esta clase proporciona métodos genéricos que pueden trabajar con cualquier tipo
 * de objeto que esté correctamente anotado para JAXB.
 */
public class XMLManager {

    /**
     * Serializa un objeto a XML y lo guarda en un archivo.
     * 
     * @param <T> Tipo genérico del objeto a serializar
     * @param objeto Objeto a serializar, debe estar anotado para JAXB
     * @param fileName Nombre del archivo donde se guardará el XML
     * @return true si la operación fue exitosa, false en caso contrario
     * @throws RuntimeException Si ocurre un error durante la serialización
     */
    public static <T> boolean writeXML(T objeto, String fileName) {
        boolean result = false;
        try {
            //Paso 1: Crear el contexto de JaxB para la clase que queremos serializar
            JAXBContext context = JAXBContext.newInstance(objeto.getClass());

            //Paso 2: proceso Marshalling: convertir objeto en XML
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(objeto,new File(fileName));
            result = true;

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Deserializa un archivo XML a un objeto.
     * 
     * @param <T> Tipo genérico del objeto a deserializar
     * @param objeto Objeto de referencia que define el tipo a deserializar
     * @param fileName Nombre del archivo XML a leer
     * @return Objeto deserializado del tipo T, o null si ocurre un error
     * @throws RuntimeException Si ocurre un error durante la deserialización
     */
    public static <T> T readXML(T objeto, String fileName) {
        T result = null;
        try {
            //Paso 1: Crear el contexto de JaxB para la clase que queremos serializar
            JAXBContext context = JAXBContext.newInstance(objeto.getClass());

            //Paso 2: Unmarshaling: leer XML y convertirlo a un objeto
            Unmarshaller unmarshaller = context.createUnmarshaller();
            result = (T) unmarshaller.unmarshal(new File(fileName));

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
