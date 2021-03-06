/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package rs.exproit.load_profile_generator.domain;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
/** Load profile (LP) data with a reference to the corresponding load condition. */
@org.apache.avro.specific.AvroGenerated
public class LoadProfileModel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -4279570843164760797L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"LoadProfileModel\",\"namespace\":\"rs.exproit.load_profile_generator.domain\",\"doc\":\"Load profile (LP) data with a reference to the corresponding load condition.\",\"fields\":[{\"name\":\"organizationId\",\"type\":[\"null\",\"string\"],\"doc\":\"The unique identifier of the power distribution company.\",\"default\":null,\"order\":\"ignore\"},{\"name\":\"consumerId\",\"type\":[\"null\",\"string\"],\"doc\":\"The unique identifier of the consumer inside the power distribution company.\",\"default\":null,\"order\":\"ignore\"},{\"name\":\"createdAt\",\"type\":\"int\",\"doc\":\"The calendar date when this LP was created.\",\"order\":\"descending\",\"logicalType\":\"date\"},{\"name\":\"deviceId\",\"type\":\"string\",\"doc\":\"The unique device identifier, which generated this LP.\"},{\"name\":\"frequency\",\"type\":\"int\",\"doc\":\"The sampling frequency in minutes.\",\"default\":15,\"order\":\"ignore\"},{\"name\":\"consumerCategory\",\"type\":{\"type\":\"enum\",\"name\":\"Category\",\"symbols\":[\"INDUSTRIAL\",\"RESIDENTIAL\"]},\"doc\":\"The category of this consumer (industrial or residential).\",\"default\":\"RESIDENTIAL\"},{\"name\":\"loadCondition\",\"type\":[\"null\",\"string\"],\"doc\":\"The identifier for the referenced data (may be null if the controller doesn't collect meta-data).\",\"default\":null,\"order\":\"ignore\"},{\"name\":\"data\",\"type\":{\"type\":\"array\",\"items\":\"double\"},\"doc\":\"The array of samples (Double.NAN if the datum is missing).\",\"order\":\"ignore\"},{\"name\":\"messageHash\",\"type\":[\"null\",{\"type\":\"fixed\",\"name\":\"MD5\",\"size\":16}],\"doc\":\"An MD5 hash value of this message (ensures data integrity).\",\"default\":null,\"order\":\"ignore\"},{\"name\":\"meta\",\"type\":[\"null\",{\"type\":\"map\",\"values\":\"bytes\"}],\"doc\":\"Arbitrary meta data attached by the smart meter.\",\"default\":null,\"order\":\"ignore\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  /** The unique identifier of the power distribution company. */
  @Deprecated public java.lang.CharSequence organizationId;
  /** The unique identifier of the consumer inside the power distribution company. */
  @Deprecated public java.lang.CharSequence consumerId;
  /** The calendar date when this LP was created. */
  @Deprecated public int createdAt;
  /** The unique device identifier, which generated this LP. */
  @Deprecated public java.lang.CharSequence deviceId;
  /** The sampling frequency in minutes. */
  @Deprecated public int frequency;
  /** The category of this consumer (industrial or residential). */
  @Deprecated public rs.exproit.load_profile_generator.domain.Category consumerCategory;
  /** The identifier for the referenced data (may be null if the controller doesn't collect meta-data). */
  @Deprecated public java.lang.CharSequence loadCondition;
  /** The array of samples (Double.NAN if the datum is missing). */
  @Deprecated public java.util.List<java.lang.Double> data;
  /** An MD5 hash value of this message (ensures data integrity). */
  @Deprecated public rs.exproit.load_profile_generator.domain.MD5 messageHash;
  /** Arbitrary meta data attached by the smart meter. */
  @Deprecated public java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> meta;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public LoadProfileModel() {}

  /**
   * All-args constructor.
   * @param organizationId The unique identifier of the power distribution company.
   * @param consumerId The unique identifier of the consumer inside the power distribution company.
   * @param createdAt The calendar date when this LP was created.
   * @param deviceId The unique device identifier, which generated this LP.
   * @param frequency The sampling frequency in minutes.
   * @param consumerCategory The category of this consumer (industrial or residential).
   * @param loadCondition The identifier for the referenced data (may be null if the controller doesn't collect meta-data).
   * @param data The array of samples (Double.NAN if the datum is missing).
   * @param messageHash An MD5 hash value of this message (ensures data integrity).
   * @param meta Arbitrary meta data attached by the smart meter.
   */
  public LoadProfileModel(java.lang.CharSequence organizationId, java.lang.CharSequence consumerId, java.lang.Integer createdAt, java.lang.CharSequence deviceId, java.lang.Integer frequency, rs.exproit.load_profile_generator.domain.Category consumerCategory, java.lang.CharSequence loadCondition, java.util.List<java.lang.Double> data, rs.exproit.load_profile_generator.domain.MD5 messageHash, java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> meta) {
    this.organizationId = organizationId;
    this.consumerId = consumerId;
    this.createdAt = createdAt;
    this.deviceId = deviceId;
    this.frequency = frequency;
    this.consumerCategory = consumerCategory;
    this.loadCondition = loadCondition;
    this.data = data;
    this.messageHash = messageHash;
    this.meta = meta;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return organizationId;
    case 1: return consumerId;
    case 2: return createdAt;
    case 3: return deviceId;
    case 4: return frequency;
    case 5: return consumerCategory;
    case 6: return loadCondition;
    case 7: return data;
    case 8: return messageHash;
    case 9: return meta;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: organizationId = (java.lang.CharSequence)value$; break;
    case 1: consumerId = (java.lang.CharSequence)value$; break;
    case 2: createdAt = (java.lang.Integer)value$; break;
    case 3: deviceId = (java.lang.CharSequence)value$; break;
    case 4: frequency = (java.lang.Integer)value$; break;
    case 5: consumerCategory = (rs.exproit.load_profile_generator.domain.Category)value$; break;
    case 6: loadCondition = (java.lang.CharSequence)value$; break;
    case 7: data = (java.util.List<java.lang.Double>)value$; break;
    case 8: messageHash = (rs.exproit.load_profile_generator.domain.MD5)value$; break;
    case 9: meta = (java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'organizationId' field.
   * @return The unique identifier of the power distribution company.
   */
  public java.lang.CharSequence getOrganizationId() {
    return organizationId;
  }

  /**
   * Sets the value of the 'organizationId' field.
   * The unique identifier of the power distribution company.
   * @param value the value to set.
   */
  public void setOrganizationId(java.lang.CharSequence value) {
    this.organizationId = value;
  }

  /**
   * Gets the value of the 'consumerId' field.
   * @return The unique identifier of the consumer inside the power distribution company.
   */
  public java.lang.CharSequence getConsumerId() {
    return consumerId;
  }

  /**
   * Sets the value of the 'consumerId' field.
   * The unique identifier of the consumer inside the power distribution company.
   * @param value the value to set.
   */
  public void setConsumerId(java.lang.CharSequence value) {
    this.consumerId = value;
  }

  /**
   * Gets the value of the 'createdAt' field.
   * @return The calendar date when this LP was created.
   */
  public java.lang.Integer getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the value of the 'createdAt' field.
   * The calendar date when this LP was created.
   * @param value the value to set.
   */
  public void setCreatedAt(java.lang.Integer value) {
    this.createdAt = value;
  }

  /**
   * Gets the value of the 'deviceId' field.
   * @return The unique device identifier, which generated this LP.
   */
  public java.lang.CharSequence getDeviceId() {
    return deviceId;
  }

  /**
   * Sets the value of the 'deviceId' field.
   * The unique device identifier, which generated this LP.
   * @param value the value to set.
   */
  public void setDeviceId(java.lang.CharSequence value) {
    this.deviceId = value;
  }

  /**
   * Gets the value of the 'frequency' field.
   * @return The sampling frequency in minutes.
   */
  public java.lang.Integer getFrequency() {
    return frequency;
  }

  /**
   * Sets the value of the 'frequency' field.
   * The sampling frequency in minutes.
   * @param value the value to set.
   */
  public void setFrequency(java.lang.Integer value) {
    this.frequency = value;
  }

  /**
   * Gets the value of the 'consumerCategory' field.
   * @return The category of this consumer (industrial or residential).
   */
  public rs.exproit.load_profile_generator.domain.Category getConsumerCategory() {
    return consumerCategory;
  }

  /**
   * Sets the value of the 'consumerCategory' field.
   * The category of this consumer (industrial or residential).
   * @param value the value to set.
   */
  public void setConsumerCategory(rs.exproit.load_profile_generator.domain.Category value) {
    this.consumerCategory = value;
  }

  /**
   * Gets the value of the 'loadCondition' field.
   * @return The identifier for the referenced data (may be null if the controller doesn't collect meta-data).
   */
  public java.lang.CharSequence getLoadCondition() {
    return loadCondition;
  }

  /**
   * Sets the value of the 'loadCondition' field.
   * The identifier for the referenced data (may be null if the controller doesn't collect meta-data).
   * @param value the value to set.
   */
  public void setLoadCondition(java.lang.CharSequence value) {
    this.loadCondition = value;
  }

  /**
   * Gets the value of the 'data' field.
   * @return The array of samples (Double.NAN if the datum is missing).
   */
  public java.util.List<java.lang.Double> getData() {
    return data;
  }

  /**
   * Sets the value of the 'data' field.
   * The array of samples (Double.NAN if the datum is missing).
   * @param value the value to set.
   */
  public void setData(java.util.List<java.lang.Double> value) {
    this.data = value;
  }

  /**
   * Gets the value of the 'messageHash' field.
   * @return An MD5 hash value of this message (ensures data integrity).
   */
  public rs.exproit.load_profile_generator.domain.MD5 getMessageHash() {
    return messageHash;
  }

  /**
   * Sets the value of the 'messageHash' field.
   * An MD5 hash value of this message (ensures data integrity).
   * @param value the value to set.
   */
  public void setMessageHash(rs.exproit.load_profile_generator.domain.MD5 value) {
    this.messageHash = value;
  }

  /**
   * Gets the value of the 'meta' field.
   * @return Arbitrary meta data attached by the smart meter.
   */
  public java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> getMeta() {
    return meta;
  }

  /**
   * Sets the value of the 'meta' field.
   * Arbitrary meta data attached by the smart meter.
   * @param value the value to set.
   */
  public void setMeta(java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> value) {
    this.meta = value;
  }

  /**
   * Creates a new LoadProfileModel RecordBuilder.
   * @return A new LoadProfileModel RecordBuilder
   */
  public static rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder newBuilder() {
    return new rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder();
  }

  /**
   * Creates a new LoadProfileModel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new LoadProfileModel RecordBuilder
   */
  public static rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder newBuilder(rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder other) {
    return new rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder(other);
  }

  /**
   * Creates a new LoadProfileModel RecordBuilder by copying an existing LoadProfileModel instance.
   * @param other The existing instance to copy.
   * @return A new LoadProfileModel RecordBuilder
   */
  public static rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder newBuilder(rs.exproit.load_profile_generator.domain.LoadProfileModel other) {
    return new rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder(other);
  }

  /**
   * RecordBuilder for LoadProfileModel instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<LoadProfileModel>
    implements org.apache.avro.data.RecordBuilder<LoadProfileModel> {

    /** The unique identifier of the power distribution company. */
    private java.lang.CharSequence organizationId;
    /** The unique identifier of the consumer inside the power distribution company. */
    private java.lang.CharSequence consumerId;
    /** The calendar date when this LP was created. */
    private int createdAt;
    /** The unique device identifier, which generated this LP. */
    private java.lang.CharSequence deviceId;
    /** The sampling frequency in minutes. */
    private int frequency;
    /** The category of this consumer (industrial or residential). */
    private rs.exproit.load_profile_generator.domain.Category consumerCategory;
    /** The identifier for the referenced data (may be null if the controller doesn't collect meta-data). */
    private java.lang.CharSequence loadCondition;
    /** The array of samples (Double.NAN if the datum is missing). */
    private java.util.List<java.lang.Double> data;
    /** An MD5 hash value of this message (ensures data integrity). */
    private rs.exproit.load_profile_generator.domain.MD5 messageHash;
    /** Arbitrary meta data attached by the smart meter. */
    private java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> meta;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.organizationId)) {
        this.organizationId = data().deepCopy(fields()[0].schema(), other.organizationId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.consumerId)) {
        this.consumerId = data().deepCopy(fields()[1].schema(), other.consumerId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[2].schema(), other.createdAt);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.deviceId)) {
        this.deviceId = data().deepCopy(fields()[3].schema(), other.deviceId);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.frequency)) {
        this.frequency = data().deepCopy(fields()[4].schema(), other.frequency);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.consumerCategory)) {
        this.consumerCategory = data().deepCopy(fields()[5].schema(), other.consumerCategory);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.loadCondition)) {
        this.loadCondition = data().deepCopy(fields()[6].schema(), other.loadCondition);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.data)) {
        this.data = data().deepCopy(fields()[7].schema(), other.data);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.messageHash)) {
        this.messageHash = data().deepCopy(fields()[8].schema(), other.messageHash);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.meta)) {
        this.meta = data().deepCopy(fields()[9].schema(), other.meta);
        fieldSetFlags()[9] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing LoadProfileModel instance
     * @param other The existing instance to copy.
     */
    private Builder(rs.exproit.load_profile_generator.domain.LoadProfileModel other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.organizationId)) {
        this.organizationId = data().deepCopy(fields()[0].schema(), other.organizationId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.consumerId)) {
        this.consumerId = data().deepCopy(fields()[1].schema(), other.consumerId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[2].schema(), other.createdAt);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.deviceId)) {
        this.deviceId = data().deepCopy(fields()[3].schema(), other.deviceId);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.frequency)) {
        this.frequency = data().deepCopy(fields()[4].schema(), other.frequency);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.consumerCategory)) {
        this.consumerCategory = data().deepCopy(fields()[5].schema(), other.consumerCategory);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.loadCondition)) {
        this.loadCondition = data().deepCopy(fields()[6].schema(), other.loadCondition);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.data)) {
        this.data = data().deepCopy(fields()[7].schema(), other.data);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.messageHash)) {
        this.messageHash = data().deepCopy(fields()[8].schema(), other.messageHash);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.meta)) {
        this.meta = data().deepCopy(fields()[9].schema(), other.meta);
        fieldSetFlags()[9] = true;
      }
    }

    /**
      * Gets the value of the 'organizationId' field.
      * The unique identifier of the power distribution company.
      * @return The value.
      */
    public java.lang.CharSequence getOrganizationId() {
      return organizationId;
    }

    /**
      * Sets the value of the 'organizationId' field.
      * The unique identifier of the power distribution company.
      * @param value The value of 'organizationId'.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder setOrganizationId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.organizationId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'organizationId' field has been set.
      * The unique identifier of the power distribution company.
      * @return True if the 'organizationId' field has been set, false otherwise.
      */
    public boolean hasOrganizationId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'organizationId' field.
      * The unique identifier of the power distribution company.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder clearOrganizationId() {
      organizationId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'consumerId' field.
      * The unique identifier of the consumer inside the power distribution company.
      * @return The value.
      */
    public java.lang.CharSequence getConsumerId() {
      return consumerId;
    }

    /**
      * Sets the value of the 'consumerId' field.
      * The unique identifier of the consumer inside the power distribution company.
      * @param value The value of 'consumerId'.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder setConsumerId(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.consumerId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'consumerId' field has been set.
      * The unique identifier of the consumer inside the power distribution company.
      * @return True if the 'consumerId' field has been set, false otherwise.
      */
    public boolean hasConsumerId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'consumerId' field.
      * The unique identifier of the consumer inside the power distribution company.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder clearConsumerId() {
      consumerId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'createdAt' field.
      * The calendar date when this LP was created.
      * @return The value.
      */
    public java.lang.Integer getCreatedAt() {
      return createdAt;
    }

    /**
      * Sets the value of the 'createdAt' field.
      * The calendar date when this LP was created.
      * @param value The value of 'createdAt'.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder setCreatedAt(int value) {
      validate(fields()[2], value);
      this.createdAt = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'createdAt' field has been set.
      * The calendar date when this LP was created.
      * @return True if the 'createdAt' field has been set, false otherwise.
      */
    public boolean hasCreatedAt() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'createdAt' field.
      * The calendar date when this LP was created.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder clearCreatedAt() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'deviceId' field.
      * The unique device identifier, which generated this LP.
      * @return The value.
      */
    public java.lang.CharSequence getDeviceId() {
      return deviceId;
    }

    /**
      * Sets the value of the 'deviceId' field.
      * The unique device identifier, which generated this LP.
      * @param value The value of 'deviceId'.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder setDeviceId(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.deviceId = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'deviceId' field has been set.
      * The unique device identifier, which generated this LP.
      * @return True if the 'deviceId' field has been set, false otherwise.
      */
    public boolean hasDeviceId() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'deviceId' field.
      * The unique device identifier, which generated this LP.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder clearDeviceId() {
      deviceId = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'frequency' field.
      * The sampling frequency in minutes.
      * @return The value.
      */
    public java.lang.Integer getFrequency() {
      return frequency;
    }

    /**
      * Sets the value of the 'frequency' field.
      * The sampling frequency in minutes.
      * @param value The value of 'frequency'.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder setFrequency(int value) {
      validate(fields()[4], value);
      this.frequency = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'frequency' field has been set.
      * The sampling frequency in minutes.
      * @return True if the 'frequency' field has been set, false otherwise.
      */
    public boolean hasFrequency() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'frequency' field.
      * The sampling frequency in minutes.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder clearFrequency() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'consumerCategory' field.
      * The category of this consumer (industrial or residential).
      * @return The value.
      */
    public rs.exproit.load_profile_generator.domain.Category getConsumerCategory() {
      return consumerCategory;
    }

    /**
      * Sets the value of the 'consumerCategory' field.
      * The category of this consumer (industrial or residential).
      * @param value The value of 'consumerCategory'.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder setConsumerCategory(rs.exproit.load_profile_generator.domain.Category value) {
      validate(fields()[5], value);
      this.consumerCategory = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'consumerCategory' field has been set.
      * The category of this consumer (industrial or residential).
      * @return True if the 'consumerCategory' field has been set, false otherwise.
      */
    public boolean hasConsumerCategory() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'consumerCategory' field.
      * The category of this consumer (industrial or residential).
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder clearConsumerCategory() {
      consumerCategory = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'loadCondition' field.
      * The identifier for the referenced data (may be null if the controller doesn't collect meta-data).
      * @return The value.
      */
    public java.lang.CharSequence getLoadCondition() {
      return loadCondition;
    }

    /**
      * Sets the value of the 'loadCondition' field.
      * The identifier for the referenced data (may be null if the controller doesn't collect meta-data).
      * @param value The value of 'loadCondition'.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder setLoadCondition(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.loadCondition = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'loadCondition' field has been set.
      * The identifier for the referenced data (may be null if the controller doesn't collect meta-data).
      * @return True if the 'loadCondition' field has been set, false otherwise.
      */
    public boolean hasLoadCondition() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'loadCondition' field.
      * The identifier for the referenced data (may be null if the controller doesn't collect meta-data).
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder clearLoadCondition() {
      loadCondition = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'data' field.
      * The array of samples (Double.NAN if the datum is missing).
      * @return The value.
      */
    public java.util.List<java.lang.Double> getData() {
      return data;
    }

    /**
      * Sets the value of the 'data' field.
      * The array of samples (Double.NAN if the datum is missing).
      * @param value The value of 'data'.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder setData(java.util.List<java.lang.Double> value) {
      validate(fields()[7], value);
      this.data = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'data' field has been set.
      * The array of samples (Double.NAN if the datum is missing).
      * @return True if the 'data' field has been set, false otherwise.
      */
    public boolean hasData() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'data' field.
      * The array of samples (Double.NAN if the datum is missing).
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder clearData() {
      data = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'messageHash' field.
      * An MD5 hash value of this message (ensures data integrity).
      * @return The value.
      */
    public rs.exproit.load_profile_generator.domain.MD5 getMessageHash() {
      return messageHash;
    }

    /**
      * Sets the value of the 'messageHash' field.
      * An MD5 hash value of this message (ensures data integrity).
      * @param value The value of 'messageHash'.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder setMessageHash(rs.exproit.load_profile_generator.domain.MD5 value) {
      validate(fields()[8], value);
      this.messageHash = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'messageHash' field has been set.
      * An MD5 hash value of this message (ensures data integrity).
      * @return True if the 'messageHash' field has been set, false otherwise.
      */
    public boolean hasMessageHash() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'messageHash' field.
      * An MD5 hash value of this message (ensures data integrity).
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder clearMessageHash() {
      messageHash = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /**
      * Gets the value of the 'meta' field.
      * Arbitrary meta data attached by the smart meter.
      * @return The value.
      */
    public java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> getMeta() {
      return meta;
    }

    /**
      * Sets the value of the 'meta' field.
      * Arbitrary meta data attached by the smart meter.
      * @param value The value of 'meta'.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder setMeta(java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer> value) {
      validate(fields()[9], value);
      this.meta = value;
      fieldSetFlags()[9] = true;
      return this;
    }

    /**
      * Checks whether the 'meta' field has been set.
      * Arbitrary meta data attached by the smart meter.
      * @return True if the 'meta' field has been set, false otherwise.
      */
    public boolean hasMeta() {
      return fieldSetFlags()[9];
    }


    /**
      * Clears the value of the 'meta' field.
      * Arbitrary meta data attached by the smart meter.
      * @return This builder.
      */
    public rs.exproit.load_profile_generator.domain.LoadProfileModel.Builder clearMeta() {
      meta = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    @Override
    public LoadProfileModel build() {
      try {
        LoadProfileModel record = new LoadProfileModel();
        record.organizationId = fieldSetFlags()[0] ? this.organizationId : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.consumerId = fieldSetFlags()[1] ? this.consumerId : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.createdAt = fieldSetFlags()[2] ? this.createdAt : (java.lang.Integer) defaultValue(fields()[2]);
        record.deviceId = fieldSetFlags()[3] ? this.deviceId : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.frequency = fieldSetFlags()[4] ? this.frequency : (java.lang.Integer) defaultValue(fields()[4]);
        record.consumerCategory = fieldSetFlags()[5] ? this.consumerCategory : (rs.exproit.load_profile_generator.domain.Category) defaultValue(fields()[5]);
        record.loadCondition = fieldSetFlags()[6] ? this.loadCondition : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.data = fieldSetFlags()[7] ? this.data : (java.util.List<java.lang.Double>) defaultValue(fields()[7]);
        record.messageHash = fieldSetFlags()[8] ? this.messageHash : (rs.exproit.load_profile_generator.domain.MD5) defaultValue(fields()[8]);
        record.meta = fieldSetFlags()[9] ? this.meta : (java.util.Map<java.lang.CharSequence,java.nio.ByteBuffer>) defaultValue(fields()[9]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
