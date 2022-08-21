package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class JpaBaseEntity { // 순수 Jpa를 이용하는 auditing class
    @Column(updatable = false) // 값이 변경되어도 db에 반영되지 않는다.
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist // persist하기 전에 실행
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
