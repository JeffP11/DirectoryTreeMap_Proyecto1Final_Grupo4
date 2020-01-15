/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.LinkedList;

/**
 *
 * @author alexispoveda
 */
public class Directory {
    private String name;
    private Double size;
    private LinkedList<Directory> listFiles;

    public Directory(String name, Double size, LinkedList<Directory> listFiles) {
        this.name = name;
        this.size = size;
        this.listFiles = listFiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public LinkedList<Directory> getListFiles() {
        return listFiles;
    }

    public void setListFiles(LinkedList<Directory> listFiles) {
        this.listFiles = listFiles;
    }

    @Override
    public String toString() {
        return "Directory{" + "name=" + name + ", size=" + size + ", listFiles=" + listFiles + '}';
    }
    
    
    
}
