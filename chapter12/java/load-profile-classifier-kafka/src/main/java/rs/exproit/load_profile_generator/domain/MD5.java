/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package rs.exproit.load_profile_generator.domain;
@SuppressWarnings("all")
@org.apache.avro.specific.FixedSize(16)
@org.apache.avro.specific.AvroGenerated
public class MD5 extends org.apache.avro.specific.SpecificFixed {
  private static final long serialVersionUID = -2432556327803693231L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"fixed\",\"name\":\"MD5\",\"namespace\":\"rs.exproit.load_profile_generator.domain\",\"size\":16}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  /** Creates a new MD5 */
  public MD5() {
    super();
  }

  /**
   * Creates a new MD5 with the given bytes.
   * @param bytes The bytes to create the new MD5.
   */
  public MD5(byte[] bytes) {
    super(bytes);
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, org.apache.avro.specific.SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, org.apache.avro.specific.SpecificData.getDecoder(in));
  }

}
