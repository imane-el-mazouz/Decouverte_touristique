//package com.tourist;
//
//import com.tourist.exception.ClientNotFoundException;
//import com.tourist.exception.ReviewNotFound;
//import com.tourist.model.Client;
//import com.tourist.model.Reservation;
//import com.tourist.model.Review;
//import com.tourist.repository.ClientRepository;
//import com.tourist.service.ClientService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class ClientTest {
//
//    @Mock
//    private ClientRepository clientRepository;
//
//    @InjectMocks
//    private ClientService clientService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testFindByEmail_Success() {
//        String email = "test@example.com";
//        Client client = new Client();
//        client.setEmail(email);
//
//        when(clientRepository.findByEmail(email)).thenReturn(client);
//
//        Client result = clientService.findByEmail(email);
//
//        assertNotNull(result);
//        assertEquals(email, result.getEmail());
//        verify(clientRepository, times(1)).findByEmail(email);
//    }
//
//    @Test
//    void testUpdateClient_Success() {
//        Long clientId = 1L;
//        Client existingClient = new Client();
//        existingClient.setId(clientId);
//        existingClient.setName("Old Name");
//        existingClient.setEmail("old@example.com");
//
//        Client updatedClient = new Client();
//        updatedClient.setName("New Name");
//        updatedClient.setEmail("new@example.com");
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
//        when(clientRepository.save(any(Client.class))).thenReturn(existingClient);
//
//        Client result = clientService.updateClient(clientId, updatedClient);
//
//        assertNotNull(result);
//        assertEquals("New Name", result.getName());
//        assertEquals("new@example.com", result.getEmail());
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(clientRepository, times(1)).save(existingClient);
//    }
//
//    @Test
//    void testUpdateClient_ClientNotFound() {
//        Long clientId = 1L;
//        Client updatedClient = new Client();
//        updatedClient.setName("New Name");
//        updatedClient.setEmail("new@example.com");
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
//
//        assertThrows(ClientNotFoundException.class, () -> clientService.updateClient(clientId, updatedClient));
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(clientRepository, never()).save(any(Client.class));
//    }
//
//    @Test
//    void testGetClientReservations_Success() {
//        Long clientId = 1L;
//        Client client = new Client();
//        Reservation reservation1 = new Reservation();
//        Reservation reservation2 = new Reservation();
//        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);
//
//        client.setReservations(reservations);
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
//
//        List<Reservation> result = clientService.getClientReservations(clientId);
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(clientRepository, times(1)).findById(clientId);
//    }
//
//    @Test
//    void testGetClientReservations_ClientNotFound() {
//        Long clientId = 1L;
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
//
//        assertThrows(ClientNotFoundException.class, () -> clientService.getClientReservations(clientId));
//        verify(clientRepository, times(1)).findById(clientId);
//    }
//
//    @Test
//    void testGetClientReviews_Success() {
//        Long clientId = 1L;
//        Client client = new Client();
//        Review review1 = new Review();
//        Review review2 = new Review();
//        List<Review> reviews = Arrays.asList(review1, review2);
//
//        client.setReviews(reviews);
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
//
//        List<Review> result = clientService.getClientReviews(clientId);
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(clientRepository, times(1)).findById(clientId);
//    }
//
//    @Test
//    void testGetClientReviews_ClientNotFound() {
//        Long clientId = 1L;
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
//
//        assertThrows(ReviewNotFound.class, () -> clientService.getClientReviews(clientId));
//        verify(clientRepository, times(1)).findById(clientId);
//    }
//}
