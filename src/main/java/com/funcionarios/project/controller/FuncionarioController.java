package com.funcionarios.project.controller;

import com.funcionarios.project.data.FuncionarioDAO;
import com.funcionarios.project.domain.Funcionario;
import java.sql.SQLException;
import java.util.List;

public class FuncionarioController {

    private final FuncionarioDAO funcionarioDao;

    // Constructor que permite la inyección de dependencia del DAO (útil para pruebas unitarias)
    public FuncionarioController(FuncionarioDAO funcionarioDao) {
        this.funcionarioDao = funcionarioDao;
    }

    // Constructor por defecto que crea una instancia de FuncionarioDAO
    public FuncionarioController() {
        this(new FuncionarioDAO());
    }

    public List<Funcionario> getListFuncionarios() throws SQLException {
        try {
            return funcionarioDao.getFuncionarios();
        } catch (SQLException e) {
            // Log de error (si es necesario)
            throw new SQLException("Error al obtener la lista de funcionarios", e);
        }
    }

    public void createFuncionario(Funcionario funcionario) throws SQLException {
        if (funcionario == null) {
            throw new IllegalArgumentException("El objeto Funcionario no puede ser nulo");
        }
        try {
            funcionarioDao.createFuncionario(funcionario);
        } catch (SQLException e) {
            // Log de error (si es necesario)
            throw new SQLException("Error al crear el funcionario", e);
        }
    }

    public Funcionario getOneFuncionario(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de Funcionario debe ser un número positivo");
        }
        try {
            Funcionario funcionario = funcionarioDao.getFuncionario(id);
            if (funcionario == null) {
                throw new SQLException("Funcionario no encontrado con ID: " + id);
            }
            return funcionario;
        } catch (SQLException e) {
            // Log de error (si es necesario)
            throw new SQLException("Error al obtener el funcionario con ID: " + id, e);
        }
    }

    public void updateFuncionario(int id, Funcionario funcionario) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de Funcionario debe ser un número positivo");
        }
        if (funcionario == null) {
            throw new IllegalArgumentException("El objeto Funcionario no puede ser nulo");
        }
        try {
            funcionarioDao.updateFuncionario(id, funcionario);
        } catch (SQLException e) {
            // Log de error (si es necesario)
            throw new SQLException("Error al actualizar el funcionario con ID: " + id, e);
        }
    }

    public void deleteFuncionario(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de Funcionario debe ser un número positivo");
        }
        try {
            funcionarioDao.deleteFuncionario(id);
        } catch (SQLException e) {
            // Log de error (si es necesario)
            throw new SQLException("Error al eliminar el funcionario con ID: " + id, e);
        }
    }
}


