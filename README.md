# Taller - JPA y Hibernate

- Este repo sirve de demo de configuración de
  JPA, [Hibernate](https://docs.jboss.org/hibernate/orm/7.0/introduction/html_single/Hibernate_Introduction.html) y
  Derby DB.
- [Hibernate 7 docs](https://docs.jboss.org/hibernate/orm/7.0/introduction/html_single/Hibernate_Introduction.html)

# Ortogonalidad / Transparencia

- La persistencia *ortogonal* significa que el desarrollador puede trabajar con objetos sin preocuparse por cómo se
  almacenan o recuperan esos objetos de la base de datos. Idealmente no debería hacer nada explícito para persistir.

# JPA: Objetos Persistentes

- ¿Qué es JPA?: Java Persistence API.
    - **Hibernate** es una implementación de JPA.
    - **ORM**: Object Relational Mapping. Nos ayuda con las diferencia de impedancia entre el modelo de objetos y el
      modelo relacional.
    - En Bases de datos Orientadas a Objetos (OODBMS) es todo super transparente, no es necesario mapear.
- Cuestiones esenciales:
    - En JPA, los objetos persistentes requieren constructores sin argumentos (protected) y setters/getters (privados)
    - Usamos Lombok para simplificar esto que se requiere para JPA.
    - Necesarios para cuando JPA crea las instacias, al recuperarlas de la base de datos.
- package `orm.context`: Describe como utilizar `EntityManagerFactory`, `EntityManager` y `EntityTransaction`.
    - Estados: Transient -> Persistent.
- package `orm.mappings`: Describe ejempos con los posibles mapeos entre un modelo de objetos un modelo relacional.
    - `orm.mappings.manytoone`: Relaciones uno a uno y muchos a uno. Solo cambia una restricción de unidad.
    - `orm.mappings.onetomany`: Es importante usar el JoinColumn para que no genere una tabla intermedia.
    - `orm.mappings.componentes`: Embebiendo objetos.
    - `orm.mappings.manytomanybidireccional`: Muchos a muchos bidireccional.
    - `orm.mappings.bidireccional`: Muchos a muchos bidireccional con datos adicionales en relación.
    - `orm.mappings.herecencia`: Single Table.
- package `orm.cascade`: Describe los diferentes forma de usar persistencia por alcance.
- package `orm.retrieval`: Describe las diferentes formas de recuperar objetos persistentes por Id.
    - `find()` vs `getReference()`
    - Lazy vs Early: Relaciones uno a muchos o muchos a muchos son lazy por defecto. Cualquier otro caso es early a
      menos que se especifique lo contrario.
    - Estados: Persistent -> Detached.
- package `orm.queries`: JPQL: Java Persistence Query Language

# Ciclo de Vida de los Objetos Persistentes

- **Transient**: Cuando creo una instancia.
- **Persistent** (managed): Cuando la instancia se persistente o se recupera de la base de datos.
- **Detached**: Cuando cierro la sesión con la base de datos, las instancias persistenes se convierten en detached.
    - Cualquier cambio sobre estas instancias desatachadas, no se persistiran en la base de datos.
- **Removed**: Cuando se elimina una instancia persistente, se marca para ser eliminada de la base de datos.

# Data Mapper / Active Record

- Active Record:
    - Cada clase representa una tabla y contiene métodos para persistir, actualizar y eliminar.
    - No oculta el hecho de que hay una base de datos relacional detrás.
    - Modelo relacional y clases coinciden uno a uno.
    - Modelo de clases y modelo relacional evolucionan de la mano.
    - Eloquent, Rails, Django.
    - Sin persistencia por alcance
- Data Mapper:
    - Un layer adicional transforma un modelo de objetos en el modelo relacional.
    - Brinda ortogonalidad, transparencia en la persistencia.
    - Modelo de objetos y relacional pueden evolucionar en forma independiente.
    - Hibernate, Doctrine (PHP).

- Un ejemplo de Eloquent (Laravel) que ilustra el concepto de Active Record:

```php
class User extends Illuminate\Database\Eloquent\Model
{
    // Relación uno-a-muchos
    public function posts()
    {
        return $this->hasMany(Post::class);
    }
}

class Post extends Illuminate\Database\Eloquent\Model
{
    // Relación muchos-a-uno
    public function user()
    {
        return $this->belongsTo(User::class);
    }
}

// Cargo un usuario existente
$user = User::find(1);

// Creo un nuevo post (NO está en la base de datos todavía)
$post = new Post(['title' => 'Nuevo Post']);

// Lo agrego a la colección en memoria
$user->posts->add($post);

// ¿Se persiste el post?
// ❌ No. Esto solo lo agrega a la colección en memoria del objeto $user.
// Si hacés commit o terminas el script, NO queda en la base de datos.

// Para persistirlo, tenés que hacer algo explícito como:
$post->user()->associate($user);
$post->save();

// O usar la relación para crear y persistir en un solo paso:
$user->posts()->save($post);

// O directamente:
$user->posts()->create(['title' => 'Nuevo Post']);
```

# Concurrencia

¿ Cómo manejar múltiples usuarios accediendo y modificando los mismos datos al mismo tiempo?

- **Pesimista**: Lockeando recursos al leerlos, liberar luego de commitear. Otros usuarios deben esperar.
- **Optimista**: Asumiendo que los conflictos son raros y manejándolos al momento de hacer commit.

- **Gestión de la concurrencia dentro de una transacción**:
    - Niveles de Aislamiento en Bases de Datos Relacionales:
        - **READ UNCOMMITTED**: permite dirty reads.
        - **READ COMMITTED**: permite lost updates. (default en la mayoría de los SGBD)
        - **REPEATABLE READ**: previene lost updates. Permite Phantom Read (default en MySQL InnoDB y Oracle)
          -- Optiminista usando MVCC (Multi Version Concurrency Control).
        - **SERIALIZABLE**: previene Phantom Reads.
    - Puedo prevenir lost updates en READ COMMITTED:
        - **Persimista**: Bloqueando filas al leerlas (SELECT ... FOR UPDATE). Lock.PESSIMISTIC_WRITE en JPA.
        - **Optimista**: Agregando un campo `@Version` en la entidad. Hibernate se encarga de incrementar el valor en
          cada actualización.
- **Gestión de la concurrencia a nivel Aplicación** (Application Level transactions).
    - Se da cuando un caso de uso se realiza utilizando 2 o más transacciones de base de datos.
    - También conocido como read-modify-write pattern.
    - Ejemplos de un CMS:
        - Tx 1: Leo un blogpost de la BD, lo llevo a la UI.
        - Think Time: El usuario lee, revisa, modifica, y finalmente presiona la acción para Guardar.
        - Tx 2: Submitea post, update en BD.
    - Dado que son dos transacciones separadas, necesito gestionar la concurrencia a nivel de aplicación.