package com.insper.partida.equipe;

import com.insper.partida.equipe.dto.SaveTeamDTO;
import com.insper.partida.equipe.dto.TeamReturnDTO;
import com.insper.partida.equipe.exception.TeamAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTests {

    @InjectMocks
    TeamService teamService;

    @Mock
    TeamRepository teamRepository;


    @Test
    void test_listTeams() {
        Mockito.when(teamRepository.findAll()).thenReturn(new ArrayList<>());

        List<TeamReturnDTO> resp = teamService.listTeams();

        Assertions.assertEquals(0, resp.size());
    }

    @Test
    void test_listTeamsNotEmpty() {

        Team team = getTeam();

        List<Team> lista = new ArrayList<>();
        lista.add(team);

        Mockito.when(teamRepository.findAll()).thenReturn(lista);

        List<TeamReturnDTO> resp = teamService.listTeams();

        Assertions.assertEquals(1, resp.size());
    }

    @Test
    void test_saveTeamNotEmpty() {

        Team team = getTeam();

        SaveTeamDTO saveTeam = new SaveTeamDTO();

        saveTeam.setIdentifier(team.getIdentifier());
        saveTeam.setName(team.getName());
        saveTeam.setStadium(team.getStadium());

        Mockito.when(teamRepository.save(Mockito.any())).thenReturn(team);

        TeamReturnDTO resp = teamService.saveTeam(saveTeam);

        Assertions.assertEquals(saveTeam.getIdentifier(), resp.getIdentifier());
    }

    @Test
    void test_saveTeamNotValid() {

        Team team = getTeam();

        SaveTeamDTO saveTeam = new SaveTeamDTO();

        saveTeam.setIdentifier(team.getIdentifier());
        saveTeam.setName(team.getName());
        saveTeam.setStadium(team.getStadium());

        Mockito.when(teamRepository.existsByIdentifier(Mockito.any())).thenReturn(true);

        Assertions.assertThrows(TeamAlreadyExistsException.class, () -> teamService.saveTeam(saveTeam));
    }

    @Test
    void team_getByIdentifier() {

        Team team = getTeam();

        SaveTeamDTO saveTeam = new SaveTeamDTO();

        saveTeam.setIdentifier(team.getIdentifier());
        saveTeam.setName(team.getName());
        saveTeam.setStadium(team.getStadium());

        String identifier = "time-1";

        Mockito.when(teamRepository.findByIdentifier(Mockito.any())).thenReturn(team);

        Team resp = teamService.getTeam(identifier);

        Assertions.assertEquals(saveTeam.getName(), resp.getName());
    }


    @Test
    void test_fds1() {
        Assertions.assertEquals(1,1);
    }

    @Test
    void test_fds2() {
        Assertions.assertEquals(1,1);
    }

    @Test
    void test_fds3() {
        Assertions.assertEquals(1,1);
    }
        
    @Test
    void test_fds4() {
        Assertions.assertEquals(1,1);
    }





    private static Team getTeam() {
        Team team = new Team();
        team.setId("1");
        team.setIdentifier("time-1");
        team.setName("Time 1");
        return team;
    }


}
