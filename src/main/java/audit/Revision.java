package audit;

import config.CustomRevisionListener;
import lombok.Data;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name= "REVISION_INFO")
@RevisionEntity(CustomRevisionListener.class)
@Data
public class Revision implements Serializable {
    private static final long serialVersionID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revision_seq")
    @SequenceGenerator(name= "revision_seq", sequenceName = "revision_sequence", allocationSize = 1)
    @RevisionNumber
    private int id;

    @Column(name="REVISION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @RevisionTimestamp
    private Date date;
}
