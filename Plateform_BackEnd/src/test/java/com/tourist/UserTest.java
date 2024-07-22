package com.tourist;

//@ExtendWith(MockitoExtension.class)
//public class UserTest {
//
//	@Mock
//	private UserRepository userRepository;
//
//	@InjectMocks
//	private UserService userService;
//
//	private User user;
//
//	@BeforeEach
//	public void setUp() {
//		user = new User();
//		user.setIdU(1L);
//		user.setName("John Doe");
//		user.setEmail("john.doe@example.com");
//	}
//
//	@Test
//	void testGetAllUsers() {
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user));
//
//		List<UserDto> users = userService.getAllUsers();
//
//		assertNotNull(users);
//		assertEquals(1, users.size());
//		assertEquals(user, users.get(0));
//		verify(userRepository, times(1)).findAll();
//	}
//
//	@Test
//	void testGetUserById() {
//		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//		User foundUser = userService.getUserById(1L);
//
//		assertNotNull(foundUser);
//		assertEquals(user, foundUser);
//		verify(userRepository, times(1)).findById(1L);
//	}
//
//	@Test
//	void testGetUserByIdNotFound() {
//		when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//		User foundUser = userService.getUserById(1L);
//
//		assertNull(foundUser);
//		verify(userRepository, times(1)).findById(1L);
//	}
//
//	@Test
//	void testSaveUser() {
//		when(userRepository.save(user)).thenReturn(user);
//
//		User savedUser = userService.saveUser(user);
//
//		assertNotNull(savedUser);
//		assertEquals(user, savedUser);
//		verify(userRepository, times(1)).save(user);
//	}
//
//	@Test
//	void testDeleteUser() {
//		doNothing().when(userRepository).deleteById(1L);
//
//		userService.deleteUser(1L);
//
//		verify(userRepository, times(1)).deleteById(1L);
//	}

