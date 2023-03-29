package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.dto.common.ApiResponse;
import com.kampusmerdeka.officeorder.dto.repsonse.BuildingResponse;
import com.kampusmerdeka.officeorder.dto.request.BuildingRequest;
import com.kampusmerdeka.officeorder.entity.*;
import com.kampusmerdeka.officeorder.repository.*;
import com.kampusmerdeka.officeorder.util.FileDeleteUtil;
import com.kampusmerdeka.officeorder.util.FileDownloadUtil;
import com.kampusmerdeka.officeorder.util.FileUploadUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class BuildingServiceTest {

    @MockBean
    private BuildingRepository buildingRepository;
    @MockBean
    private ComplexRepository complexRepository;
    @MockBean
    private FacilityRepository facilityRepository;
    @MockBean
    private BuildingFacilityRepository buildingFacilityRepository;
    @MockBean
    private BuildingImageRepository buildingImageRepository;
    @Autowired
    private BuildingService buildingService;

    private static MockedStatic<FileDownloadUtil> fileDownloadMock;
    private static MockedStatic<FileUploadUtil> fileUploadMock;
    private static MockedStatic<FileDeleteUtil> fileDeleteMock;
    private static MockMultipartFile image;

    @SneakyThrows
    @BeforeAll
    public static void init() {
        fileDownloadMock = Mockito.mockStatic(FileDownloadUtil.class);
        fileUploadMock = Mockito.mockStatic(FileUploadUtil.class);
        fileDeleteMock = Mockito.mockStatic(FileDeleteUtil.class);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.jpg");
        image = new MockMultipartFile("test.jpg", "test.jpg", "image/jpg", inputStream);
    }

    @AfterAll
    public static void close() {
        fileDownloadMock.close();
        fileUploadMock.close();
        fileDeleteMock.close();
    }

    @Test
    void getAll() {
        Complex complex = Complex.builder().id(1L).name("ANY").city(City.builder().id(1L).name("ANY").build()).build();
        Building building1 = Building.builder().id(1L).name("ANY 1").description("ANY DESCRIPTION 1").complex(complex).build();
        Building building2 = Building.builder().id(1L).name("ANY 2").description("ANY DESCRIPTION 2").complex(complex).build();
        Building building3 = Building.builder().id(1L).name("ANY 3").description("ANY DESCRIPTION 3").complex(complex).build();

        building1.setBuildingImages(Set.of(
                BuildingImage.builder().id(1L).building(building1).name("NAME").path("path/to/file.jpg").build(),
                BuildingImage.builder().id(2L).building(building1).name("NAME").path("path/to/file.jpg").build(),
                BuildingImage.builder().id(3L).building(building1).name("NAME").path("path/to/file.jpg").build()
        ));
        building2.setBuildingImages(Set.of(
                BuildingImage.builder().id(1L).building(building2).name("NAME").path("path/to/file.jpg").build(),
                BuildingImage.builder().id(2L).building(building2).name("NAME").path("path/to/file.jpg").build(),
                BuildingImage.builder().id(3L).building(building2).name("NAME").path("path/to/file.jpg").build()
        ));
        building3.setBuildingImages(Set.of(
                BuildingImage.builder().id(1L).building(building3).name("NAME").path("path/to/file.jpg").build(),
                BuildingImage.builder().id(2L).building(building3).name("NAME").path("path/to/file.jpg").build(),
                BuildingImage.builder().id(3L).building(building3).name("NAME").path("path/to/file.jpg").build()
        ));

        List<Building> buildings = Arrays.asList(building1, building2, building3);

        when(buildingRepository.findAll()).thenReturn(buildings);

        ResponseEntity<Object> responseEntity = buildingService.getAll();
        ApiResponse body = (ApiResponse) responseEntity.getBody();
        List<BuildingResponse> data = (List<BuildingResponse>) body.getData();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(buildings.size(), data.size());
    }

    @Test
    void getOne_Success() {
        Complex complex = Complex.builder().id(1L).name("ANY").city(City.builder().id(1L).name("ANY").build()).build();
        Building building = Building.builder().id(1L).name("ANY 1").description("ANY DESCRIPTION 1").complex(complex).build();
        List<BuildingImage> buildingImages = Arrays.asList(
                BuildingImage.builder().id(1L).building(building).name("NAME").path("path/to/file.jpg").build(),
                BuildingImage.builder().id(2L).building(building).name("NAME").path("path/to/file.jpg").build(),
                BuildingImage.builder().id(3L).building(building).name("NAME").path("path/to/file.jpg").build()
        );

        when(buildingRepository.findById(anyLong())).thenReturn(Optional.of(building));
        when(buildingImageRepository.findByBuilding(any(Building.class))).thenReturn(buildingImages);

        ResponseEntity<Object> responseEntity = buildingService.getOne(1L);
        ApiResponse body = (ApiResponse) responseEntity.getBody();
        BuildingResponse data = (BuildingResponse) body.getData();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(data.getId(), 1L);
    }

    @Test
    void getOne_NotFound() {
        when(buildingRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = buildingService.getOne(1L);
        ApiResponse body = (ApiResponse) responseEntity.getBody();
        BuildingResponse data = (BuildingResponse) body.getData();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void createOne_Success() {
        Complex complex = Complex.builder()
                .id(1L)
                .name("ANY")
                .city(City.builder().id(1L).name("ANY").build())
                .build();

        Facility facility = Facility.builder()
                .id(1L)
                .name("NAME")
                .build();

        BuildingRequest request = BuildingRequest.builder()
                .complexId(1L)
                .name("NAME")
                .description("DESCRIPTION")
                .address("ADDRESS")
                .facilities(List.of(1L))
                .images(List.of(image, image))
                .build();

        Building building = Building.builder()
                .id(1L)
                .name(request.getName())
                .description(request.getDescription())
                .complex(complex)
                .build();

        BuildingFacility buildingFacility = BuildingFacility.builder()
                .id(1L)
                .facility(facility)
                .building(building)
                .build();

        BuildingImage buildingImage = BuildingImage.builder()
                .id(1L)
                .name("ANYTHING")
                .building(building)
                .path("path-to-file.jpg")
                .build();

        building.setBuildingFacilities(Set.of(buildingFacility));
        building.setBuildingImages(Set.of(buildingImage));

        when(complexRepository.findById(anyLong())).thenReturn(Optional.of(complex));
        when(facilityRepository.findById(anyLong())).thenReturn(Optional.of(facility));
        when(buildingRepository.saveAndFlush(any(Building.class))).thenReturn(building);
        when(buildingImageRepository.findByBuilding(any(Building.class))).thenReturn(List.of(buildingImage));
        when(buildingFacilityRepository.findByBuilding(any(Building.class))).thenReturn(List.of(buildingFacility));

        ResponseEntity<Object> responseEntity = buildingService.createOne(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void createOne_ComplexNotFound() {
        Facility facility = Facility.builder()
                .id(1L)
                .name("NAME")
                .build();

        BuildingRequest request = BuildingRequest.builder()
                .complexId(1L)
                .name("NAME")
                .description("DESCRIPTION")
                .address("ADDRESS")
                .facilities(Arrays.asList(1L))
                .images(Arrays.asList(image))
                .build();

        when(complexRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(facilityRepository.findById(anyLong())).thenReturn(Optional.of(facility));

        ResponseEntity<Object> responseEntity = buildingService.createOne(request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void updateOne_Success() {
        Complex complex = Complex.builder()
                .id(1L)
                .name("ANY")
                .city(City.builder().id(1L).name("ANY").build())
                .build();

        Facility facility = Facility.builder()
                .id(1L)
                .name("NAME")
                .build();

        Building building = Building.builder()
                .id(1L).name("ANY 1")
                .description("ANY DESCRIPTION 1")
                .complex(complex)
                .buildingFacilities(Set.of())
                .build();

        BuildingFacility buildingFacility = BuildingFacility.builder()
                .id(1L)
                .facility(facility)
                .building(building)
                .build();

        building.setBuildingFacilities(Set.of(buildingFacility));

        BuildingRequest request = BuildingRequest.builder()
                .complexId(1L)
                .name("NAME")
                .description("DESCRIPTION")
                .address("ADDRESS")
                .facilities(Arrays.asList(1L))
                .images(Arrays.asList(image, image))
                .build();

        when(buildingRepository.findById(anyLong())).thenReturn(Optional.of(building));
        when(complexRepository.findById(anyLong())).thenReturn(Optional.of(complex));
        when(facilityRepository.findById(anyLong())).thenReturn(Optional.of(facility));
        when(buildingRepository.saveAndFlush(any(Building.class))).thenReturn(building);

        ResponseEntity<Object> responseEntity = buildingService.updateOne(1L, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateOne_NotFound() {
        Complex complex = Complex.builder()
                .id(1L)
                .name("ANY")
                .city(City.builder().id(1L).name("ANY").build())
                .build();

        Facility facility = Facility.builder()
                .id(1L)
                .name("NAME")
                .build();

        Building building = Building.builder()
                .id(1L).name("ANY 1")
                .description("ANY DESCRIPTION 1")
                .complex(complex)
                .buildingFacilities(Set.of())
                .build();

        BuildingFacility buildingFacility = BuildingFacility.builder()
                .id(1L)
                .facility(facility)
                .building(building)
                .build();

        building.setBuildingFacilities(Set.of(buildingFacility));

        BuildingRequest request = BuildingRequest.builder()
                .complexId(1L)
                .name("NAME")
                .description("DESCRIPTION")
                .address("ADDRESS")
                .facilities(Arrays.asList(1L))
                .images(Arrays.asList(image, image))
                .build();

        when(buildingRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = buildingService.updateOne(1L, request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void updateOne_ComplexNotFound() {
        Complex complex = Complex.builder()
                .id(1L)
                .name("ANY")
                .city(City.builder().id(1L).name("ANY").build())
                .build();

        Facility facility = Facility.builder()
                .id(1L)
                .name("NAME")
                .build();

        Building building = Building.builder()
                .id(1L).name("ANY 1")
                .description("ANY DESCRIPTION 1")
                .complex(complex)
                .buildingFacilities(Set.of())
                .build();

        BuildingFacility buildingFacility = BuildingFacility.builder()
                .id(1L)
                .facility(facility)
                .building(building)
                .build();

        building.setBuildingFacilities(Set.of(buildingFacility));

        BuildingRequest request = BuildingRequest.builder()
                .complexId(1L)
                .name("NAME")
                .description("DESCRIPTION")
                .address("ADDRESS")
                .facilities(Arrays.asList(1L))
                .images(Arrays.asList(image, image))
                .build();

        when(buildingRepository.findById(anyLong())).thenReturn(Optional.of(building));
        when(complexRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(facilityRepository.findById(anyLong())).thenReturn(Optional.of(facility));

        ResponseEntity<Object> responseEntity = buildingService.updateOne(1L, request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void deleteOne_Success() {
        Complex complex = Complex.builder().id(1L).name("ANY").city(City.builder().id(1L).name("ANY").build()).build();
        Building building = Building.builder().id(1L).name("ANY 1").description("ANY DESCRIPTION 1").complex(complex).build();
        List<BuildingImage> buildingImages = Arrays.asList(
                BuildingImage.builder().id(1L).building(building).name("NAME").path("path/to/file.jpg").build(),
                BuildingImage.builder().id(2L).building(building).name("NAME").path("path/to/file.jpg").build(),
                BuildingImage.builder().id(3L).building(building).name("NAME").path("path/to/file.jpg").build()
        );

        when(buildingRepository.findById(anyLong())).thenReturn(Optional.of(building));
        when(buildingImageRepository.findByBuilding_Id(anyLong())).thenReturn(buildingImages);
        fileDeleteMock.when(() -> FileDeleteUtil.delete(anyString())).thenAnswer((Answer<Void>) invovation -> null);

        ResponseEntity<Object> responseEntity = buildingService.deleteOne(1L);

        verify(buildingRepository, times(1)).deleteById(building.getId());
        fileDeleteMock.verify(() -> FileDeleteUtil.delete(anyString()), times(3));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteOne_NotFound() {
        when(buildingRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = buildingService.deleteOne(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}