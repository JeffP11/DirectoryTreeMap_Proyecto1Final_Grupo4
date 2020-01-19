package Clases;

import java.util.LinkedList;

/**
 *
 * @author alexispoveda
 */
public class Directory {

    private String name;
    private Double tamaño;
    private LinkedList<Directory> directorios;

    public Directory(String name, Double tamaño) {
        this.name = name;
        this.tamaño = tamaño;
        this.directorios = new LinkedList<Directory>();
    }

    public Directory(String name) {
        this.name = name;
        this.tamaño = 0.0;
        this.directorios = new LinkedList<Directory>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSize() {
        return tamaño;
    }

    public void setSize(Double size) {
        this.tamaño = size;
    }

    public LinkedList<Directory> getDirectorios() {
        return directorios;
    }

    public void setDirectorios(LinkedList<Directory> directorios) {
        this.directorios = directorios;
    }

    @Override
    public String toString() {
        return "Directory{" + "name=" + name + ", size=" + tamaño + ", directorios=" + directorios + '}';
    }

}
