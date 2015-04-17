package estore;

import estore.FacadeComp;
import estore.ProviderComp;
import estore.TraceCompQ3;
import interfaces.IProvider;
import question3.interfaces.ITrace;

@SuppressWarnings("all")
public abstract class ProviderCompositeCompQ3 extends ProviderComp {
  public interface Component extends ProviderComp.Component, ProviderCompositeCompQ3.Provides {
  }
  
  public interface Provides extends ProviderComp.Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IProvider providing();
  }
  
  public interface Parts extends ProviderComp.Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public FacadeComp.Component fc();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public TraceCompQ3.Component tr();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ProviderComp.Component pv();
  }
  
  public static class ComponentImpl extends ProviderComp.ComponentImpl implements ProviderCompositeCompQ3.Component, ProviderCompositeCompQ3.Parts {
    private final ProviderComp.Requires bridge;
    
    private final ProviderCompositeCompQ3 implementation;
    
    @Override
    public void start() {
      super.start();
      assert this.fc != null: "This is a bug.";
      ((FacadeComp.ComponentImpl) this.fc).start();
      assert this.tr != null: "This is a bug.";
      ((TraceCompQ3.ComponentImpl) this.tr).start();
      assert this.pv != null: "This is a bug.";
      ((ProviderComp.ComponentImpl) this.pv).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_fc() {
      assert this.fc == null: "This is a bug.";
      assert this.implem_fc == null: "This is a bug.";
      this.implem_fc = this.implementation.make_fc();
      if (this.implem_fc == null) {
      	throw new RuntimeException("make_fc() in estore.ProviderCompositeCompQ3 should not return null.");
      }
      this.fc = this.implem_fc._newComponent(new BridgeImpl_fc(), false);
      
    }
    
    private void init_tr() {
      assert this.tr == null: "This is a bug.";
      assert this.implem_tr == null: "This is a bug.";
      this.implem_tr = this.implementation.make_tr();
      if (this.implem_tr == null) {
      	throw new RuntimeException("make_tr() in estore.ProviderCompositeCompQ3 should not return null.");
      }
      this.tr = this.implem_tr._newComponent(new BridgeImpl_tr(), false);
      
    }
    
    private void init_pv() {
      assert this.pv == null: "This is a bug.";
      assert this.implem_pv == null: "This is a bug.";
      this.implem_pv = this.implementation.make_pv();
      if (this.implem_pv == null) {
      	throw new RuntimeException("make_pv() in estore.ProviderCompositeCompQ3 should not return null.");
      }
      this.pv = this.implem_pv._newComponent(new BridgeImpl_pv(), false);
      
    }
    
    @Override
    protected void initParts() {
      super.initParts();
      init_fc();
      init_tr();
      init_pv();
    }
    
    @Override
    protected void initProvidedPorts() {
      super.initProvidedPorts();
    }
    
    public ComponentImpl(final ProviderCompositeCompQ3 implem, final ProviderComp.Requires b, final boolean doInits) {
      super(implem, b, false);
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    @Override
    public IProvider providing() {
      // it's ok to cast because make_providing()
      // fill the parent class providing field with the correct type
      return (IProvider) super.providing();
    }
    
    private FacadeComp.Component fc;
    
    private FacadeComp implem_fc;
    
    private final class BridgeImpl_fc implements FacadeComp.Requires {
      public final IProvider provider() {
        return ProviderCompositeCompQ3.ComponentImpl.this.pv().providing();
      }
      
      public final ITrace trace() {
        return ProviderCompositeCompQ3.ComponentImpl.this.tr().trace();
      }
    }
    
    public final FacadeComp.Component fc() {
      return this.fc;
    }
    
    private TraceCompQ3.Component tr;
    
    private TraceCompQ3 implem_tr;
    
    private final class BridgeImpl_tr implements TraceCompQ3.Requires {
    }
    
    public final TraceCompQ3.Component tr() {
      return this.tr;
    }
    
    private ProviderComp.Component pv;
    
    private ProviderComp implem_pv;
    
    private final class BridgeImpl_pv implements ProviderComp.Requires {
    }
    
    public final ProviderComp.Component pv() {
      return this.pv;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private ProviderCompositeCompQ3.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  @Override
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  @Override
  protected ProviderCompositeCompQ3.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  @Override
  protected ProviderComp.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  @Override
  protected ProviderCompositeCompQ3.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract FacadeComp make_fc();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract TraceCompQ3 make_tr();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ProviderComp make_pv();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  @Override
  public synchronized ProviderCompositeCompQ3.Component _newComponent(final ProviderComp.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ProviderCompositeCompQ3 has already been used to create a component, use another one.");
    }
    this.init = true;
    ProviderCompositeCompQ3.ComponentImpl  _comp = new ProviderCompositeCompQ3.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  @Override
  public ProviderCompositeCompQ3.Component newComponent() {
    return this._newComponent(new ProviderComp.Requires() {}, true);
  }
}
