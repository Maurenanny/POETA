package mx.edu.utez.poeta.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vacancies")
public class Vacancies implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;

    @Column(name = "date_start", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "date_end", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;

    @Column(name = "status", nullable = false)
    private boolean status;

    @ManyToOne()
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    /* @Column(name = "type", nullable = false)
    private int type; */
    @ManyToOne()
    @JoinColumn(name = "type", nullable = false)
    private VacancyType type;

    @Column(name = "job_start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobStartDate;

    @Column(name = "min_salary", nullable = false)
    private double minSalary;

    @Column(name = "max_salary", nullable = false)
    private double maxSalary;

    @ManyToOne()
    @JoinColumn(name = "payment_period", nullable = false)
    private VacancyPaymentPeriod paymentPeriod;

    /* @Column(name = "payment_period", nullable = false)
    private int paymentPeriod; */

    @Column(name = "benefits", nullable = false)
    private String benefits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(User recruiter) {
        this.recruiter = recruiter;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public VacancyType getType() {
        return type;
    }

    public void setType(VacancyType type) {
        this.type = type;
    }

    public Date getJobStartDate() {
        return jobStartDate;
    }

    public void setJobStartDate(Date jobStartDate) {
        this.jobStartDate = jobStartDate;
    }

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public VacancyPaymentPeriod getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(VacancyPaymentPeriod paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
    
}
