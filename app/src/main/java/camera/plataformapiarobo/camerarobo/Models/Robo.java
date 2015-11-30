package camera.plataformapiarobo.camerarobo.Models;

/**
 * Created by Lince on 12/11/2015.
 */
public class Robo {
    private String nomeRobo;
    private String idRobo;
    private String idPaciente;
    private String nomePaciente;
    private String descricao;

    public String getNomeRobo() {
        return nomeRobo;
    }

    public void setNomeRobo(String nome) {
        this.nomeRobo = nome;
    }

    public String getIdRobo() {
        return idRobo;
    }

    public void setIdRobo(String id) {
        this.idRobo = id;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }
}
