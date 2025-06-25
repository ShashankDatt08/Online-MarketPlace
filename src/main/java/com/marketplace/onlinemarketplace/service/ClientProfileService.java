
@Transactional
public void deleteProfile(Long id) {
    Optional<ClientProfile> existingProfile = clientProfileRepo.findById(id);
    if (existingProfile.isEmpty()) {
        throw new RuntimeException("Client profile not found");
    }
    clientProfileRepo.deleteById(id);
}
