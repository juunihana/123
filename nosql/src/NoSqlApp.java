public class NoSqlApp {

  private static final Logger log = Logger.getInstance();

  public static void main(String[] args) {
    HinodeDB db = new HinodeDB();
    db.dispatch("make doc first_doc");
    db.dispatch("store name:alice in first_doc");
    db.dispatch("store surname:mikryukova in first_doc");
    db.dispatch("store age:25 in first_doc");

    db.dispatch("make doc second_doc");
    db.dispatch("store name:kira in second_doc");
    db.dispatch("store surname:hotaru in second_doc");
    db.dispatch("store age:27 in second_doc");

    db.dispatch("make doc third_doc");
    db.dispatch("store sender:alice in third_doc");
    db.dispatch("store recipient:kira in third_doc");
    db.dispatch("store content:hello in third_doc");

    log.info(db.dispatch("find sender in third_doc"));

    db.dispatch("remove doc third_doc");

    log.info(db.dispatch("find sender in third_doc"));

    db.dispatch("remove name:surname in first_doc");

    log.info(db.dispatch("find __all__ in first_doc"));

    db.dispatch("store surname:mikryukova in first_doc");
    db.dispatch("store city:moscow in first_doc");
    db.dispatch("store planet:earth in first_doc");
    db.dispatch("store occupation:engineer in first_doc");
    db.dispatch("store info:human in first_doc");
    db.dispatch("store key:value in first_doc");

    log.info(db.dispatch("find __all__ in first_doc"));
  }
}
