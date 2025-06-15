package model;

import java.util.List;

public interface Observado {
    void registrarObservador(Observador o);
    void removerObservador(Observador o);
    void notificarObservadores();
}
