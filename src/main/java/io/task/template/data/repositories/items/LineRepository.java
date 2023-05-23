package io.task.template.data.repositories.items;

import io.task.template.data.entities.items.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LineRepository extends JpaRepository<Line, String> {

    void deleteByIdIn(List<String> ids);

}
