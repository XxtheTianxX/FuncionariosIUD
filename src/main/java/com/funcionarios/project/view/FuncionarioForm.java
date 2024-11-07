package com.funcionarios.project.view;

import javax.swing.*;
import java.awt.*;
import com.funcionarios.project.controller.FuncionarioController;
import com.funcionarios.project.domain.Funcionario;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import com.toedter.calendar.JDateChooser;

public class FuncionarioForm extends JFrame {

    private final FuncionarioController funcionarioController;

    // Componentes de la primera pestaña
    private JComboBox<String> tipoIdentificacionComboBox;
    private JTextField numeroIdentificacionField, nombresField, apellidosField, direccionField, telefonoField;
    private JComboBox<String> estadoCivilComboBox, sexoComboBox;
    private JDateChooser fechaNacimientoChooser;
    private JTable funcionariosTable;

    // Componentes de la segunda pestaña
    private JComboBox<Funcionario> funcionariosComboBox;

    // Componentes del formulario de actualización
    private JTextField numeroIdentificacionFieldUpdate, nombresFieldUpdate, apellidosFieldUpdate, direccionFieldUpdate, telefonoFieldUpdate;
    private JComboBox<String> tipoIdentificacionComboBoxUpdate, estadoCivilComboBoxUpdate, sexoComboBoxUpdate;
    private JDateChooser fechaNacimientoChooserUpdate;


    public FuncionarioForm(FuncionarioController funcionarioController) {
        this.funcionarioController = funcionarioController;
        initUI();
    }

    private void initUI() {
        setTitle("Gestión de Funcionarios");
        setLayout(new BorderLayout());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Primera pestaña: Crear y listar funcionarios
        JPanel createAndListPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        tipoIdentificacionComboBox = new JComboBox<>(new String[]{"CC", "TI", "CE", "Pasaporte"});
        numeroIdentificacionField = new JTextField(20);
        nombresField = new JTextField(20);
        apellidosField = new JTextField(20);
        estadoCivilComboBox = new JComboBox<>(new String[]{"Soltero(a)", "Casado(a)", "Divorciado(a)", "Viudo(a)"});
        sexoComboBox = new JComboBox<>(new String[]{"Masculino", "Femenino","Otros"});
        direccionField = new JTextField(20);
        telefonoField = new JTextField(20);
        fechaNacimientoChooser = new JDateChooser();
        fechaNacimientoChooser.setDateFormatString("yyyy-MM-dd");

        JButton createButton = new JButton("Crear Funcionario");
        createButton.addActionListener(e -> createFuncionario());

        // Colocar componentes en el formulario
        formPanel.add(new JLabel("Tipo de Identificación:"), gbc); gbc.gridx = 1; formPanel.add(tipoIdentificacionComboBox, gbc);
        gbc.gridy = 1; gbc.gridx = 0; formPanel.add(new JLabel("Número de Identificación:"), gbc); gbc.gridx = 1; formPanel.add(numeroIdentificacionField, gbc);
        gbc.gridy = 2; gbc.gridx = 0; formPanel.add(new JLabel("Nombres:"), gbc); gbc.gridx = 1; formPanel.add(nombresField, gbc);
        gbc.gridy = 3; gbc.gridx = 0; formPanel.add(new JLabel("Apellidos:"), gbc); gbc.gridx = 1; formPanel.add(apellidosField, gbc);
        gbc.gridy = 4; gbc.gridx = 0; formPanel.add(new JLabel("Estado Civil:"), gbc); gbc.gridx = 1; formPanel.add(estadoCivilComboBox, gbc);
        gbc.gridy = 5; gbc.gridx = 0; formPanel.add(new JLabel("Sexo:"), gbc); gbc.gridx = 1; formPanel.add(sexoComboBox, gbc);
        gbc.gridy = 6; gbc.gridx = 0; formPanel.add(new JLabel("Dirección:"), gbc); gbc.gridx = 1; formPanel.add(direccionField, gbc);
        gbc.gridy = 7; gbc.gridx = 0; formPanel.add(new JLabel("Teléfono:"), gbc); gbc.gridx = 1; formPanel.add(telefonoField, gbc);
        gbc.gridy = 8; gbc.gridx = 0; formPanel.add(new JLabel("Fecha de Nacimiento:"), gbc); gbc.gridx = 1; formPanel.add(fechaNacimientoChooser, gbc);
        gbc.gridy = 9; gbc.gridwidth = 2; formPanel.add(createButton, gbc);

        // Tabla de funcionarios
        funcionariosTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(funcionariosTable);
        createAndListPanel.add(formPanel, BorderLayout.NORTH);
        createAndListPanel.add(scrollPane, BorderLayout.CENTER);

        // Segunda pestaña: Actualizar o eliminar funcionario
        JPanel updateDeletePanel = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Título "Seleccionar Funcionario"
        gbc.gridy = 0; gbc.gridx = 0;
        updateDeletePanel.add(new JLabel("Seleccionar Funcionario:"), gbc);

        // ComboBox para seleccionar funcionario
        funcionariosComboBox = new JComboBox<>();
        funcionariosComboBox.addActionListener(e -> loadFuncionarioDetails()); // Aquí se llama a loadFuncionarioDetails cuando cambia la selección
        gbc.gridx = 3;
        updateDeletePanel.add(funcionariosComboBox, gbc);

        // Campos de formulario para actualización
        gbc.gridy = 1; gbc.gridx = 0; updateDeletePanel.add(new JLabel("Número Identificación:"), gbc);
        tipoIdentificacionComboBoxUpdate = new JComboBox<>(new String[]{"CC", "TI", "CE", "Pasaporte"});
        gbc.gridx = 3; updateDeletePanel.add(tipoIdentificacionComboBoxUpdate, gbc);

        gbc.gridy = 2; gbc.gridx = 0; updateDeletePanel.add(new JLabel("Número Identificación:"), gbc);
        numeroIdentificacionFieldUpdate = new JTextField(20);
        gbc.gridx = 3; updateDeletePanel.add(numeroIdentificacionFieldUpdate, gbc);

        gbc.gridy = 3; gbc.gridx = 0; updateDeletePanel.add(new JLabel("Nombres:"), gbc);
        nombresFieldUpdate = new JTextField(20);
        gbc.gridx = 3; updateDeletePanel.add(nombresFieldUpdate, gbc);

        gbc.gridy = 4; gbc.gridx = 0; updateDeletePanel.add(new JLabel("Apellidos:"), gbc);
        apellidosFieldUpdate = new JTextField(20);
        gbc.gridx = 3; updateDeletePanel.add(apellidosFieldUpdate, gbc);

        gbc.gridy = 5; gbc.gridx = 0; updateDeletePanel.add(new JLabel("Estado Civil:"), gbc);
        estadoCivilComboBoxUpdate = new JComboBox<>(new String[]{"Soltero(a)", "Casado(a)", "Divorciado(a)", "Viudo(a)"});
        gbc.gridx = 3; updateDeletePanel.add(estadoCivilComboBoxUpdate, gbc);

        gbc.gridy = 6; gbc.gridx = 0; updateDeletePanel.add(new JLabel("Sexo:"), gbc);
        sexoComboBoxUpdate = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        gbc.gridx = 3; updateDeletePanel.add(sexoComboBoxUpdate, gbc);

        gbc.gridy = 7; gbc.gridx = 0; updateDeletePanel.add(new JLabel("Dirección:"), gbc);
        direccionFieldUpdate = new JTextField(20);
        gbc.gridx = 3; updateDeletePanel.add(direccionFieldUpdate, gbc);

        gbc.gridy = 8; gbc.gridx = 0; updateDeletePanel.add(new JLabel("Teléfono:"), gbc);
        telefonoFieldUpdate = new JTextField(20);
        gbc.gridx = 3; updateDeletePanel.add(telefonoFieldUpdate, gbc);

        gbc.gridy = 9; gbc.gridx = 0; updateDeletePanel.add(new JLabel("Fecha Nacimiento:"), gbc);
        fechaNacimientoChooserUpdate = new JDateChooser();
        fechaNacimientoChooserUpdate.setDateFormatString("yyyy-MM-dd");
        gbc.gridx = 3; updateDeletePanel.add(fechaNacimientoChooserUpdate, gbc);

        // Botón de actualización
        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(e -> updateFuncionario());
        gbc.gridy = 10; gbc.gridx = 1;
        updateDeletePanel.add(updateButton, gbc);

        // Botón de eliminación
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> deleteFuncionario());
        gbc.gridy = 10; gbc.gridx = 3;
        updateDeletePanel.add(deleteButton, gbc);


        // Añadir la segunda pestaña al JTabbedPane
        tabbedPane.addTab("Crear y Listar Funcionarios", createAndListPanel);
        tabbedPane.addTab("Actualizar o Eliminar Funcionario", updateDeletePanel);

        // Añadir pestañas al JFrame
        add(tabbedPane, BorderLayout.CENTER);

        // Cargar funcionarios en el ComboBox
        loadFuncionariosList();
    }

    private void loadFuncionarioDetails() {
        Funcionario selectedFuncionario = (Funcionario) funcionariosComboBox.getSelectedItem();
        if (selectedFuncionario != null) {
            numeroIdentificacionFieldUpdate.setText(selectedFuncionario.getNumeroIdentificacion());
            nombresFieldUpdate.setText(selectedFuncionario.getNombres());
            apellidosFieldUpdate.setText(selectedFuncionario.getApellidos());
            estadoCivilComboBoxUpdate.setSelectedItem(selectedFuncionario.getEstadoCivil());
            sexoComboBoxUpdate.setSelectedItem(selectedFuncionario.getSexo());
            direccionFieldUpdate.setText(selectedFuncionario.getDireccion());
            telefonoFieldUpdate.setText(selectedFuncionario.getTelefono());

            LocalDate fechaNacimientoLocalDate = selectedFuncionario.getFechaNacimiento();
            if (fechaNacimientoLocalDate != null) {
                Date fechaNacimientoDate = Date.from(fechaNacimientoLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                fechaNacimientoChooserUpdate.setDate(fechaNacimientoDate);
            } else {
                fechaNacimientoChooserUpdate.setDate(null);
            }

        }
    }


    private void createFuncionario() {
        if (isFormValid()) {
            try {
                Funcionario funcionario = new Funcionario();
                funcionario.setTipoIdentificacion(Objects.requireNonNull(tipoIdentificacionComboBox.getSelectedItem()).toString());
                funcionario.setNumeroIdentificacion(numeroIdentificacionField.getText().trim());
                funcionario.setNombres(nombresField.getText().trim());
                funcionario.setApellidos(apellidosField.getText().trim());
                funcionario.setEstadoCivil(Objects.requireNonNull(estadoCivilComboBox.getSelectedItem()).toString());
                funcionario.setSexo(Objects.requireNonNull(sexoComboBox.getSelectedItem()).toString().charAt(0));
                funcionario.setDireccion(direccionField.getText().trim());
                funcionario.setTelefono(telefonoField.getText().trim());
                funcionario.setFechaNacimiento(new java.sql.Date(fechaNacimientoChooser.getDate().getTime()).toLocalDate());

                funcionarioController.createFuncionario(funcionario);
                JOptionPane.showMessageDialog(this, "Funcionario creado con éxito");
                clearFormFields();
                loadFuncionariosList();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al crear funcionario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isFormValid() {
        return !numeroIdentificacionField.getText().trim().isEmpty() &&
                !nombresField.getText().trim().isEmpty() &&
                !apellidosField.getText().trim().isEmpty() &&
                !direccionField.getText().trim().isEmpty() &&
                !telefonoField.getText().trim().isEmpty() &&
                fechaNacimientoChooser.getDate() != null;
    }

    private boolean isUpdateFormValid() {
        return !numeroIdentificacionFieldUpdate.getText().trim().isEmpty() &&
                !nombresFieldUpdate.getText().trim().isEmpty() &&
                !apellidosFieldUpdate.getText().trim().isEmpty() &&
                !direccionFieldUpdate.getText().trim().isEmpty() &&
                !telefonoFieldUpdate.getText().trim().isEmpty() &&
                fechaNacimientoChooserUpdate.getDate() != null;
    }


    private void updateFuncionario() {
        Funcionario selectedFuncionario = (Funcionario) funcionariosComboBox.getSelectedItem();
        if (selectedFuncionario != null && isUpdateFormValid()) {
            try {
                selectedFuncionario.setTipoIdentificacion(Objects.requireNonNull(tipoIdentificacionComboBoxUpdate.getSelectedItem()).toString());
                selectedFuncionario.setNumeroIdentificacion(numeroIdentificacionFieldUpdate.getText().trim());
                selectedFuncionario.setNombres(nombresFieldUpdate.getText().trim());
                selectedFuncionario.setApellidos(apellidosFieldUpdate.getText().trim());
                selectedFuncionario.setEstadoCivil(Objects.requireNonNull(estadoCivilComboBoxUpdate.getSelectedItem()).toString());
                selectedFuncionario.setSexo(Objects.requireNonNull(sexoComboBoxUpdate.getSelectedItem()).toString().charAt(0));
                selectedFuncionario.setDireccion(direccionFieldUpdate.getText().trim());
                selectedFuncionario.setTelefono(telefonoFieldUpdate.getText().trim());
                selectedFuncionario.setFechaNacimiento(new java.sql.Date(fechaNacimientoChooserUpdate.getDate().getTime()).toLocalDate());

                funcionarioController.updateFuncionario(selectedFuncionario.getIdFuncionario(), selectedFuncionario);
                JOptionPane.showMessageDialog(this, "Funcionario actualizado exitosamente");
                clearUpdateFormFields();
                isUpdateFormValid();
                loadFuncionariosList();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar funcionario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void clearUpdateFormFields() {
        tipoIdentificacionComboBoxUpdate.setSelectedIndex(0);
        numeroIdentificacionFieldUpdate.setText("");
        nombresFieldUpdate.setText("");
        apellidosFieldUpdate.setText("");
        estadoCivilComboBoxUpdate.setSelectedIndex(0);
        sexoComboBoxUpdate.setSelectedIndex(0);
        direccionFieldUpdate.setText("");
        telefonoFieldUpdate.setText("");
        fechaNacimientoChooserUpdate.setDate(null);
    }

    private void deleteFuncionario() {
        Funcionario selectedFuncionario = (Funcionario) funcionariosComboBox.getSelectedItem();
        if (selectedFuncionario != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este funcionario?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    funcionarioController.deleteFuncionario(selectedFuncionario.getIdFuncionario());
                    JOptionPane.showMessageDialog(this, "Funcionario eliminado con éxito");
                    loadFuncionariosList();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar funcionario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void loadFuncionariosList() {
        try {
            List<Funcionario> funcionarios = funcionarioController.getListFuncionarios();
            funcionariosComboBox.removeAllItems();
            for (Funcionario funcionario : funcionarios) {
                funcionariosComboBox.addItem(funcionario);
            }

            String[] columnNames = {"Tipo Identificación", "Numero de Identificacion", "Nombre", "Apellidos", "Estado Civil", "Sexo", "Dirección", "Teléfono", "Fecha Nacimiento"};
            Object[][] data = new Object[funcionarios.size()][9];
            for (int i = 0; i < funcionarios.size(); i++) {
                Funcionario f = funcionarios.get(i);
                data[i][0] = f.getTipoIdentificacion();
                data[i][1] = f.getNumeroIdentificacion();
                data[i][2] = f.getNombres();
                data[i][3] = f.getApellidos();
                data[i][4] = f.getEstadoCivil();
                data[i][5] = f.getSexo();
                data[i][6] = f.getDireccion();
                data[i][7] = f.getTelefono();
                data[i][8] = f.getFechaNacimiento();
            }
            funcionariosTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la lista de funcionarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFormFields() {
        tipoIdentificacionComboBox.setSelectedIndex(0);
        numeroIdentificacionField.setText("");
        nombresField.setText("");
        apellidosField.setText("");
        estadoCivilComboBox.setSelectedIndex(0);
        sexoComboBox.setSelectedIndex(0);
        direccionField.setText("");
        telefonoField.setText("");
        fechaNacimientoChooser.setDate(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FuncionarioController funcionarioController = new FuncionarioController();
            FuncionarioForm funcionarioForm = new FuncionarioForm(funcionarioController);
            funcionarioForm.setVisible(true);
        });
    }
}

