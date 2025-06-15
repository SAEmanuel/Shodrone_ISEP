package persistence.jpa;

import domain.entity.ShowProposal;
import domain.valueObjects.Name;
import domain.valueObjects.ShowProposalStatus;

import java.sql.*;
import java.util.Optional;

public class ShowProposalJDBCImpl {

    private static final String URL = "jdbc:postgresql://10.9.23.21:5432/shodrone_db";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Optional<ShowProposal> findByID(Long id) {
        String sql = """
                SELECT show_proposal_id, name, show_proposal_status
                FROM showproposal
                WHERE show_proposal_id = ?
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ShowProposal proposal = new ShowProposal();

                proposal.editShowProposalID(rs.getLong("show_proposal_id"));
                proposal.setNameProposal(new Name(rs.getString("name")));
                proposal.setStatus(ShowProposalStatus.valueOf(rs.getString("show_proposal_status")));

                return Optional.of(proposal);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar proposta:");
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<ShowProposal> saveInStore(ShowProposal entity) {
        String sql = """
                UPDATE showproposal
                SET show_proposal_status = ?
                WHERE show_proposal_id = ?
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getStatus().name());
            stmt.setLong(2, entity.identity());

            int updated = stmt.executeUpdate();
            return updated > 0 ? Optional.of(entity) : Optional.empty();

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar proposta:");
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
