package it.infocube.management.profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Short> {
    Short id(Short id);
}
