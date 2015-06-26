package com.dabast.common.web.jacson;

/**
 * Created by IntelliJ IDEA.
 * User: lxd
 * Date: 10-12-31
 * Time: 下午3:46
 * To change this template use File | Settings | File Templates.
 */
public class HibernateAwareSerializerFactory 
//extends BeanSerializerFactory 
{
//	protected HibernateAwareSerializerFactory(Config config) {
//		super(config);
//		
//	}
//
//	/**
//     * Name of the property added during build-time byte-code instrumentation
//     * by Hibernate. It must be filtered out.
//     */
//    private static final String FIELD_HANDLER_PROPERTY_NAME = "fieldHandler";
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public JsonSerializer createSerializer(JavaType type, SerializationConfig config) {
//        Class<?> clazz = type.getRawClass();
//
//        //check for all Hibernate proxy invariants and build custom serializers for them
//        if (PersistentCollection.class.isAssignableFrom(clazz)) {
//            return new PersistentCollectionSerializer(type, config);
//        }
//
//        if (HibernateProxy.class.isAssignableFrom(clazz)) {
//            return new HibernateProxySerializer(type, config);
//        }
//
//        if (ICodeEnum.class.isAssignableFrom(clazz)) {
//            return new ICodeEnumSerializer();
//        }
//
//        //Well, then it is not a Hibernate proxy
//        return super.createSerializer(type, config);
//    }
//
//    /**
//     * The purpose of this method is to filter out {@link javax.persistence.Transient} properties of the bean
//     * from JSON rendering.
//     */
//    @Override
//    protected List<BeanPropertyWriter> filterBeanProperties(SerializationConfig config,
//                                                            BasicBeanDescription beanDesc,
//                                                            List<BeanPropertyWriter> props) {
//
//        //filter out standard properties (e.g. those marked with @JsonIgnore)
//        props = super.filterBeanProperties(config, beanDesc, props); //NOSONAR
//
//        filterInstrumentedBeanProperties(beanDesc, props);
//
//        //now filter out the @Transient ones as they may trigger "lazy" exceptions by
//        //referencing non-initialized properties
//        List<String> transientOnes = new ArrayList<String>();
//        //BeanUtils and AnnotationUtils are utility methods that come from
//        //the Spring Framework
//        for (PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(beanDesc.getBeanClass())) {
//            Method getter = pd.getReadMethod();
//            if (getter != null && AnnotationUtils.findAnnotation(getter, Transient.class) != null) {
//                transientOnes.add(pd.getName());
//            }
//        }
//
//        //remove transient
//        for (Iterator<BeanPropertyWriter> iter = props.iterator(); iter.hasNext();) {
//            if (transientOnes.contains(iter.next().getName())) {
//                iter.remove();
//            }
//        }
//
//        return props;
//    }
//
//    private void filterInstrumentedBeanProperties(BasicBeanDescription beanDesc,
//                                                  List<BeanPropertyWriter> props) {
//
//        //all beans that have build-time instrumented lazy-loaded properties
//        //will implement FieldHandled interface.
//        if (!FieldHandled.class.isAssignableFrom(beanDesc.getBeanClass())) {
//            return;
//        }
//
//        //remove fieldHandler bean property from JSON serialization as it causes
//        //infinite recursion
//        for (Iterator<BeanPropertyWriter> iter = props.iterator(); iter.hasNext();) {
//            if (iter.next().getName().equals(FIELD_HANDLER_PROPERTY_NAME)) {
//                iter.remove();
//            }
//        }
//    }
//
//    private static final class ICodeEnumSerializer extends JsonSerializer<Object> {
////        private final JavaType type;
////        private final SerializationConfig config;
////
////        private ICodeEnumSerializer(JavaType type, SerializationConfig config) {
////            this.type = type;
////            this.config = config;
////        }
//
//
//        @Override
//        @SuppressWarnings("unchecked")
//        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
//        	jgen.writeString(((ICodeEnum)value).toCode());
//        }
//    }        
//
//    /**
//     * The purpose of this class is to perform graceful handling of custom Hibernate collections.
//     */
//    private final class PersistentCollectionSerializer extends JsonSerializer<Object> {
//        private final JavaType type;
//        private final SerializationConfig config;
//
//        private PersistentCollectionSerializer(JavaType type, SerializationConfig config) {
//            this.type = type;
//            this.config = config;
//        }
//
//
//        @Override
//        @SuppressWarnings("unchecked")
//        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
//            //avoid lazy initialization exceptions
//            if (!((PersistentCollection) value).wasInitialized()) {
//                jgen.writeNull();
//                return;
//            }
//            
//            //construct an actual serializer from the built-in ones
//            BasicBeanDescription beanDesc = config.introspect(type);
//            Class<?> clazz = type.getRawClass();
//
//            JsonSerializer<Object> serializer;
//            if (PersistentMap.class.isAssignableFrom(clazz)) {
//                serializer = (JsonSerializer<Object>) buildMapSerializer(type, config, beanDesc);
//            }
//            else {
//                serializer = (JsonSerializer<Object>) buildCollectionSerializer(type, config, beanDesc);
//            }
//
//            //delegate serialization to a built-in serializer
//            serializer.serialize(value, jgen, provider);
//        }
//    }
//
//    /**
//     * The purpose of this class is to perform graceful handling of HibernateProxy objects.
//     */
//    private final class HibernateProxySerializer extends JsonSerializer<Object> {
//        private final JavaType type;
//        private final SerializationConfig config;
//
//        private HibernateProxySerializer(JavaType type, SerializationConfig config) {
//            this.type = type;
//            this.config = config;
//        }
//
//        @Override
//        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
//        	if(value instanceof LazyInitializer){
//        		return;
//        	}
//            if (((HibernateProxy) value).getHibernateLazyInitializer().isUninitialized()) {
//                jgen.writeNull();
//                return;
//            }
//
//            BasicBeanDescription beanDesc = config.introspect(type);
//            JsonSerializer<Object> serializer = findBeanSerializer(type, config, beanDesc);
//
//            //delegate serialization to a build-in serializer
//            serializer.serialize(value, jgen, provider);
//        }
//    }
    }






