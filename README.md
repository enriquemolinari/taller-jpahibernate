# Taller - JPA y Hibernate

- Este repo sirve de demo de configuración de
  JPA, [Hibernate](https://docs.jboss.org/hibernate/orm/7.0/introduction/html_single/Hibernate_Introduction.html) y
  Derby DB.
- [Hibernate 7 docs](https://docs.jboss.org/hibernate/orm/7.0/introduction/html_single/Hibernate_Introduction.html)

# Ortogonalidad / Transparencia

- La persistencia *ortogonal* significa que el desarrollador puede trabajar con objetos sin preocuparse por cómo se
  almacenan o recuperan esos objetos de la base de datos. Idealmente no debería hacer nada explícito para persistir.

# JPA: Objetos Persistentes

## Entidades

Es obligatorio tener un constructor vacío (lo ponemos protegido) y setters y getters (privados).

```java

@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    ...

    //no-arg constructor requerido
    protected Persona() {
    }
    ...

    //get-set privados
}
```

## Componentes

No requieren definir un id en el `embeddable`.

```java

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    @Embedded
    private Address address;
}

@Embeddable
public class Address {
    private String address;
    ...
}
```

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