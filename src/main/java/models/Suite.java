package models;

import java.util.List;

public class Suite {
    private String id;
    private String nome;
    private boolean persistSession;
    private boolean parallel;
    private int timeout;
    private List<String> tests;

    public List<String> getTests() {
        return tests;
    }

    public void setTests(List<String> tests) {
        this.tests = tests;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isPersistSession() {
        return persistSession;
    }

    public void setPersistSession(boolean persistSession) {
        this.persistSession = persistSession;
    }

    public boolean isParallel() {
        return parallel;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }


}
