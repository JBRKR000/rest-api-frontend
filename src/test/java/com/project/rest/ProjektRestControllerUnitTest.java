package com.project.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.project.model.Projekt;
import com.project.controller.ProjektWebController;
import com.project.service.ProjektService;

@ExtendWith(MockitoExtension.class)

public class ProjektRestControllerUnitTest
{

    @Mock
    private ProjektService mockProjektService;

    @InjectMocks
    private ProjektWebController projectController;

    // TEST - getProject
    @Test
    void getProject_whenValidId_shouldReturnGivenProject()
    {
        Projekt projekt = new Projekt(1, "Nazwa1", "Opis1", LocalDate.of(2024, 6, 7));
        when(mockProjektService.getProjekt(projekt.getProjektId())).thenReturn(Optional.of(projekt));

        ResponseEntity < Projekt > responseEntity = projectController.getProjekt(projekt.getProjektId());
        assertAll(() -> assertEquals(responseEntity.getStatusCode().value(), HttpStatus.OK.value()),

                () -> assertEquals(responseEntity.getBody(), projekt));
    }

    // TEST - getProject
    @Test
    void getProject_whenInvalidId_shouldReturnNotFound()
    {
        Integer projektId = 2;
        when(mockProjektService.getProjekt(projektId)).thenReturn(Optional.empty());

        ResponseEntity < Projekt > responseEntity = projectController.getProjekt(projektId);
        assertEquals(responseEntity.getStatusCode().value(), HttpStatus.NOT_FOUND.value());

    }

    // TEST - getProjects
    @Test
    //@DisplayName("Should return the page containing projects")
    void getProjects_shouldReturnPageWithProjects()
    {
        List < Projekt > list =
                List.of(new Projekt(1, "Nazwa1", "Opis1", LocalDate.of(2024, 6, 7)),
                        new Projekt(2, "Nazwa2", "Opis2", LocalDate.of(2024, 6, 7)),
                        new Projekt(3, "Nazwa3", "Opis3", LocalDate.of(2024, 6, 7)));
        PageRequest pageable = PageRequest.of(1, 5);
        Page < Projekt > page = new PageImpl < > (list, pageable, 5);
        when(mockProjektService.getProjekty(pageable)).thenReturn(page);
        Page < Projekt > pageWithProjects = ProjektWebController.getProjekty(pageable);
        assertNotNull(pageWithProjects);
        List < Projekt > projects = pageWithProjects.getContent();
        assertNotNull(projects);
        assertThat(projects, hasSize(3));
        assertAll(() -> assertTrue(projects.contains(list.get(0))),
                () -> assertTrue(projects.contains(list.get(1))),
                () -> assertTrue(projects.contains(list.get(2))));
        // W przypadku assertAll wszystkie asercje przekazane jako argumenty zostaną
        // wykonane, nawet gdy jedna z pierwszych da wynik negatywny, a jeśli choć
        // jedna zakończy się wyjątkiem, to cały test zakończy się błędem.
    }

    // TEST - createProject
    @Test
    void createProject_whenValidData_shouldCreateProject()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Projekt projekt = new Projekt(1, "Nazwa1", "Opis1", LocalDate.of(2023, 6, 7));
        when(mockProjektService.setProjekt(any(Projekt.class))).thenReturn(projekt);

        ResponseEntity < Void > responseEntity = projectController.createProjekt(projekt);
        assertThat(responseEntity.getStatusCode().value(), is(HttpStatus.CREATED.value()));
        assertThat(responseEntity.getHeaders().getLocation().getPath(), is("/" +
                projekt.getProjektId()));
    }

    // TEST - updateProject
    @Test
    void updateProject_whenValidData_shouldUpdateProject()
    {
        Projekt projekt = new Projekt(1, "Nazwa1", "Opis1", LocalDate.of(2024, 6, 7));
        when(mockProjektService.getProjekt(projekt.getProjektId())).thenReturn(Optional.of(projekt));

        ResponseEntity < Void > responseEntity = projectController.updateProjekt(projekt.getProjektId(), projekt); // !!!
        assertThat(responseEntity.getStatusCode().value(), is(HttpStatus.OK.value()));
    }

}