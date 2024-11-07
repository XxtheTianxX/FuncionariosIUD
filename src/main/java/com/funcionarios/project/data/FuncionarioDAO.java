package com.funcionarios.project.data;

import com.funcionarios.project.config.ConnectionConfig;
import com.funcionarios.project.domain.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private static final String GET_FUNCIONARIOS = "SELECT * FROM Funcionario";
    private static final String CREATE_FUNCIONARIO = "INSERT INTO Funcionario (TipoIdentificacion, NumeroIdentificacion, Nombres, Apellidos, EstadoCivil, Sexo, Direccion, Telefono, FechaNacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_FUNCIONARIO_BY_ID = "SELECT * FROM Funcionario WHERE IdFuncionario = ?";
    private static final String UPDATE_FUNCIONARIO = "UPDATE Funcionario SET TipoIdentificacion = ?, NumeroIdentificacion = ?, Nombres = ?, Apellidos = ?, EstadoCivil = ?, Sexo = ?, Direccion = ?, Telefono = ?, FechaNacimiento = ? WHERE IdFuncionario = ?";
    private static final String DELETE_FUNCIONARIO = "DELETE FROM Funcionario WHERE IdFuncionario = ?";

    public List<Funcionario> getFuncionarios() throws SQLException {

        List<Funcionario> funcionarios = new ArrayList<>();
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FUNCIONARIOS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(resultSet.getInt("IdFuncionario"));
                funcionario.setTipoIdentificacion(resultSet.getString("TipoIdentificacion"));
                funcionario.setNumeroIdentificacion(resultSet.getString("NumeroIdentificacion"));
                funcionario.setNombres(resultSet.getString("Nombres"));
                funcionario.setApellidos(resultSet.getString("Apellidos"));
                funcionario.setEstadoCivil(resultSet.getString("EstadoCivil"));
                funcionario.setSexo(resultSet.getString("Sexo").charAt(0));
                funcionario.setDireccion(resultSet.getString("Direccion"));
                funcionario.setTelefono(resultSet.getString("Telefono"));
                funcionario.setFechaNacimiento(resultSet.getDate("FechaNacimiento").toLocalDate());
                funcionarios.add(funcionario);
            }
        }
        return funcionarios;
    }

    public void createFuncionario(Funcionario funcionario) throws SQLException {

        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FUNCIONARIO)) {

            preparedStatement.setString(1, funcionario.getTipoIdentificacion());
            preparedStatement.setString(2, funcionario.getNumeroIdentificacion());
            preparedStatement.setString(3, funcionario.getNombres());
            preparedStatement.setString(4, funcionario.getApellidos());
            preparedStatement.setString(5, funcionario.getEstadoCivil());
            preparedStatement.setString(6, String.valueOf(funcionario.getSexo()));
            preparedStatement.setString(7, funcionario.getDireccion());
            preparedStatement.setString(8, funcionario.getTelefono());
            preparedStatement.setDate(9, java.sql.Date.valueOf(funcionario.getFechaNacimiento()));
            preparedStatement.executeUpdate();
        }
    }

    public Funcionario getFuncionario(int id) throws SQLException {

        Funcionario funcionario = null;
        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FUNCIONARIO_BY_ID)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    funcionario = new Funcionario();
                    funcionario.setIdFuncionario(resultSet.getInt("IdFuncionario"));
                    funcionario.setTipoIdentificacion(resultSet.getString("TipoIdentificacion"));
                    funcionario.setNumeroIdentificacion(resultSet.getString("NumeroIdentificacion"));
                    funcionario.setNombres(resultSet.getString("Nombres"));
                    funcionario.setApellidos(resultSet.getString("Apellidos"));
                    funcionario.setEstadoCivil(resultSet.getString("EstadoCivil"));
                    funcionario.setSexo(resultSet.getString("Sexo").charAt(0));
                    funcionario.setDireccion(resultSet.getString("Direccion"));
                    funcionario.setTelefono(resultSet.getString("Telefono"));
                    funcionario.setFechaNacimiento(resultSet.getDate("FechaNacimiento").toLocalDate());
                }
            }
        }
        return funcionario;
    }

    public void updateFuncionario(int id, Funcionario funcionario) throws SQLException {

        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FUNCIONARIO)) {

            preparedStatement.setString(1, funcionario.getTipoIdentificacion());
            preparedStatement.setString(2, funcionario.getNumeroIdentificacion());
            preparedStatement.setString(3, funcionario.getNombres());
            preparedStatement.setString(4, funcionario.getApellidos());
            preparedStatement.setString(5, funcionario.getEstadoCivil());
            preparedStatement.setString(6, String.valueOf(funcionario.getSexo()));
            preparedStatement.setString(7, funcionario.getDireccion());
            preparedStatement.setString(8, funcionario.getTelefono());
            preparedStatement.setDate(9, java.sql.Date.valueOf(funcionario.getFechaNacimiento()));
            preparedStatement.setInt(10, id);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteFuncionario(int id) throws SQLException {

        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FUNCIONARIO)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
