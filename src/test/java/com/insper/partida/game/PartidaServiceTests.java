package com.insper.partida.game;

import com.insper.partida.equipe.Team;
import com.insper.partida.equipe.TeamRepository;
import com.insper.partida.equipe.TeamService;
import com.insper.partida.equipe.dto.TeamReturnDTO;
import com.insper.partida.game.dto.EditGameDTO;
import com.insper.partida.game.dto.GameReturnDTO;
import com.insper.partida.game.dto.SaveGameDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PartidaServiceTests {

    @InjectMocks
    GameService gameService;

    @Mock
    GameRepository gameRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    TeamService teamService;


    @Test
    void test_saveGame() {
        SaveGameDTO saveGameDTO = new SaveGameDTO();

        saveGameDTO.setHome("Flamengo");
        saveGameDTO.setAway("Bangu");

        Team flamengo = new Team();

        flamengo.setStadium("Maracanã");
        flamengo.setIdentifier("Flamengo");
        flamengo.setName("Clube de Regatas do Flamengo");

        Team bangu = new Team();

        bangu.setStadium("Moça Bonita");
        bangu.setIdentifier("Bangu");
        bangu.setName("Bangu Atlético Clube");

        Mockito.when(teamService.getTeam(saveGameDTO.getHome())).thenReturn(flamengo);
        Mockito.when(teamService.getTeam(saveGameDTO.getAway())).thenReturn(bangu);


        GameReturnDTO resp = gameService.saveGame(saveGameDTO);

        Assertions.assertEquals(flamengo.getIdentifier(), resp.getHome());
        Assertions.assertEquals(bangu.getIdentifier(), resp.getAway());
    }

    @Test
    void test_saveGameNull() {
        SaveGameDTO saveGameDTO = new SaveGameDTO();

        saveGameDTO.setHome("Flamengo");
        saveGameDTO.setAway("Bangu");

        Team flamengo = new Team();

        flamengo.setStadium("Maracanã");
        flamengo.setIdentifier("Flamengo");
        flamengo.setName("Clube de Regatas do Flamengo");

        Mockito.when(teamService.getTeam(saveGameDTO.getHome())).thenReturn(null);
        Mockito.when(teamService.getTeam(saveGameDTO.getAway())).thenReturn(null);

        GameReturnDTO resp = gameService.saveGame(saveGameDTO);

        Assertions.assertNull(resp);
    }

    @Test
    void test_saveGameOneNull() {
        SaveGameDTO saveGameDTO = new SaveGameDTO();

        saveGameDTO.setHome("Flamengo");
        saveGameDTO.setAway(null);

        Team flamengo = new Team();

        flamengo.setStadium("Maracanã");
        flamengo.setIdentifier("Flamengo");
        flamengo.setName("Clube de Regatas do Flamengo");

        Mockito.when(teamService.getTeam(saveGameDTO.getHome())).thenReturn(flamengo);
        Mockito.when(teamService.getTeam(saveGameDTO.getAway())).thenReturn(null);

        GameReturnDTO resp = gameService.saveGame(saveGameDTO);

        Assertions.assertNull(resp);
    }


    @Test
    void test_editGame() {

        Team flamengo = new Team();

        flamengo.setStadium("Maracanã");
        flamengo.setIdentifier("Flamengo");
        flamengo.setName("Clube de Regatas do Flamengo");

        Team bangu = new Team();

        bangu.setStadium("Moça Bonita");
        bangu.setIdentifier("Bangu");
        bangu.setName("Bangu Atlético Clube");

        Game game = new Game();
        game.setIdentifier(UUID.randomUUID().toString());
        game.setHome(flamengo.getIdentifier());
        game.setAway(bangu.getIdentifier());
        game.setAttendance(0);
        game.setScoreHome(0);
        game.setScoreAway(0);
        game.setGameDate(LocalDateTime.now());
        game.setStatus("SCHEDULED");

        Game gameReturn = new Game();
        gameReturn.setIdentifier(UUID.randomUUID().toString());
        gameReturn.setHome(flamengo.getIdentifier());
        gameReturn.setAway(bangu.getIdentifier());
        gameReturn.setAttendance(180000);
        gameReturn.setScoreHome(5);
        gameReturn.setScoreAway(0);
        gameReturn.setGameDate(LocalDateTime.now());
        gameReturn.setStatus("SCHEDULED");

        Mockito.when(gameRepository.findByIdentifier(game.getIdentifier())).thenReturn(game);
        Mockito.when(gameRepository.existsByIdentifier(game.getIdentifier())).thenReturn(true);
        Mockito.when(gameRepository.save(Mockito.any())).thenReturn(game);


        EditGameDTO edit = new EditGameDTO();
        edit.setAttendance(180000);
        edit.setScoreAway(0);
        edit.setScoreHome(5);

        GameReturnDTO resp = gameService.editGame(game.getIdentifier(),edit);

        Assertions.assertEquals(edit.getScoreHome(), resp.getScoreHome());
        Assertions.assertEquals(edit.getScoreAway(), resp.getScoreAway());
    }

    @Test
    void test_editGameNull() {


        Team flamengo = new Team();

        flamengo.setStadium("Maracanã");
        flamengo.setIdentifier("Flamengo");
        flamengo.setName("Clube de Regatas do Flamengo");

        Team bangu = new Team();

        bangu.setStadium("Moça Bonita");
        bangu.setIdentifier("Bangu");
        bangu.setName("Bangu Atlético Clube");

        Game game = new Game();
        game.setIdentifier(UUID.randomUUID().toString());
        game.setHome(flamengo.getIdentifier());
        game.setAway(bangu.getIdentifier());
        game.setAttendance(0);
        game.setScoreHome(0);
        game.setScoreAway(0);
        game.setGameDate(LocalDateTime.now());
        game.setStatus("SCHEDULED");

        Mockito.when(gameRepository.existsByIdentifier(Mockito.any())).thenReturn(false);

        EditGameDTO edit = new EditGameDTO();
        edit.setAttendance(180000);
        edit.setScoreAway(0);
        edit.setScoreHome(5);

        GameReturnDTO resp = gameService.editGame(game.getIdentifier(),edit);

        Assertions.assertNull(resp);
    }

    @Test
    void test_deleteGame() {
        SaveGameDTO saveGameDTO = new SaveGameDTO();

        saveGameDTO.setHome("Flamengo");
        saveGameDTO.setAway("Bangu");

        Team flamengo = new Team();

        flamengo.setStadium("Maracanã");
        flamengo.setIdentifier("Flamengo");
        flamengo.setName("Clube de Regatas do Flamengo");

        Team bangu = new Team();

        bangu.setStadium("Moça Bonita");
        bangu.setIdentifier("Bangu");
        bangu.setName("Bangu Atlético Clube");

        Game game = new Game();
        game.setIdentifier(UUID.randomUUID().toString());
        game.setHome(flamengo.getIdentifier());
        game.setAway(bangu.getIdentifier());
        game.setAttendance(0);
        game.setScoreHome(0);
        game.setScoreAway(0);
        game.setGameDate(LocalDateTime.now());
        game.setStatus("SCHEDULED");

        Game gameReturn = new Game();
        gameReturn.setIdentifier(UUID.randomUUID().toString());
        gameReturn.setHome(flamengo.getIdentifier());
        gameReturn.setAway(bangu.getIdentifier());
        gameReturn.setAttendance(180000);
        gameReturn.setScoreHome(5);
        gameReturn.setScoreAway(0);
        gameReturn.setGameDate(LocalDateTime.now());
        gameReturn.setStatus("SCHEDULED");

        Mockito.when(gameRepository.findByIdentifier(game.getIdentifier())).thenReturn(game);
        Mockito.when(gameRepository.existsByIdentifier(game.getIdentifier())).thenReturn(true);
        Mockito.when(gameRepository.save(Mockito.any())).thenReturn(game);


        EditGameDTO edit = new EditGameDTO();
        edit.setAttendance(180000);
        edit.setScoreAway(0);
        edit.setScoreHome(5);

        GameReturnDTO resp = gameService.editGame(game.getIdentifier(),edit);

        Assertions.assertEquals(edit.getScoreHome(), resp.getScoreHome());
        Assertions.assertEquals(edit.getScoreAway(), resp.getScoreAway());
    }
}
