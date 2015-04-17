package banking;

import banking.DeskComp;
import banking.FacadeCompP2;
import estore.TraceCompQ3;
import interfaces.IAccountBasicOperation;
import interfaces.IBankTransfert;
import question3.interfaces.ITrace;

@SuppressWarnings("all")
public abstract class DeskCompP2 extends DeskComp {
  public interface Component extends DeskComp.Component, DeskCompP2.Provides {
  }
  
  public interface Provides extends DeskComp.Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IBankTransfert deskTransfert();
  }
  
  public interface Parts extends DeskComp.Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public DeskComp.Component dc();
    
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
    public FacadeCompP2.Component fc();
  }
  
  public static class ComponentImpl extends DeskComp.ComponentImpl implements DeskCompP2.Component, DeskCompP2.Parts {
    private final DeskComp.Requires bridge;
    
    private final DeskCompP2 implementation;
    
    @Override
    public void start() {
      super.start();
      assert this.dc != null: "This is a bug.";
      ((DeskComp.ComponentImpl) this.dc).start();
      assert this.tr != null: "This is a bug.";
      ((TraceCompQ3.ComponentImpl) this.tr).start();
      assert this.fc != null: "This is a bug.";
      ((FacadeCompP2.ComponentImpl) this.fc).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_dc() {
      assert this.dc == null: "This is a bug.";
      assert this.implem_dc == null: "This is a bug.";
      this.implem_dc = this.implementation.make_dc();
      if (this.implem_dc == null) {
      	throw new RuntimeException("make_dc() in banking.DeskCompP2 should not return null.");
      }
      this.dc = this.implem_dc._newComponent(new BridgeImpl_dc(), false);
      
    }
    
    private void init_tr() {
      assert this.tr == null: "This is a bug.";
      assert this.implem_tr == null: "This is a bug.";
      this.implem_tr = this.implementation.make_tr();
      if (this.implem_tr == null) {
      	throw new RuntimeException("make_tr() in banking.DeskCompP2 should not return null.");
      }
      this.tr = this.implem_tr._newComponent(new BridgeImpl_tr(), false);
      
    }
    
    private void init_fc() {
      assert this.fc == null: "This is a bug.";
      assert this.implem_fc == null: "This is a bug.";
      this.implem_fc = this.implementation.make_fc();
      if (this.implem_fc == null) {
      	throw new RuntimeException("make_fc() in banking.DeskCompP2 should not return null.");
      }
      this.fc = this.implem_fc._newComponent(new BridgeImpl_fc(), false);
      
    }
    
    @Override
    protected void initParts() {
      super.initParts();
      init_dc();
      init_tr();
      init_fc();
    }
    
    @Override
    protected void initProvidedPorts() {
      super.initProvidedPorts();
    }
    
    public ComponentImpl(final DeskCompP2 implem, final DeskComp.Requires b, final boolean doInits) {
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
    public IBankTransfert deskTransfert() {
      // it's ok to cast because make_deskTransfert()
      // fill the parent class deskTransfert field with the correct type
      return (IBankTransfert) super.deskTransfert();
    }
    
    private DeskComp.Component dc;
    
    private DeskComp implem_dc;
    
    private final class BridgeImpl_dc implements DeskComp.Requires {
      public final IAccountBasicOperation anneAccount() {
        return DeskCompP2.ComponentImpl.this.bridge.anneAccount();
      }
      
      public final IAccountBasicOperation bobAccount() {
        return DeskCompP2.ComponentImpl.this.bridge.bobAccount();
      }
      
      public final IAccountBasicOperation storeAccount() {
        return DeskCompP2.ComponentImpl.this.bridge.storeAccount();
      }
    }
    
    public final DeskComp.Component dc() {
      return this.dc;
    }
    
    private TraceCompQ3.Component tr;
    
    private TraceCompQ3 implem_tr;
    
    private final class BridgeImpl_tr implements TraceCompQ3.Requires {
    }
    
    public final TraceCompQ3.Component tr() {
      return this.tr;
    }
    
    private FacadeCompP2.Component fc;
    
    private FacadeCompP2 implem_fc;
    
    private final class BridgeImpl_fc implements FacadeCompP2.Requires {
      public final IBankTransfert deskPort() {
        return DeskCompP2.ComponentImpl.this.dc().deskTransfert();
      }
      
      public final ITrace trace() {
        return DeskCompP2.ComponentImpl.this.tr().trace();
      }
    }
    
    public final FacadeCompP2.Component fc() {
      return this.fc;
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
  
  private DeskCompP2.ComponentImpl selfComponent;
  
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
  protected DeskCompP2.Provides provides() {
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
  protected DeskComp.Requires requires() {
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
  protected DeskCompP2.Parts parts() {
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
  protected abstract DeskComp make_dc();
  
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
  protected abstract FacadeCompP2 make_fc();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  @Override
  public synchronized DeskCompP2.Component _newComponent(final DeskComp.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of DeskCompP2 has already been used to create a component, use another one.");
    }
    this.init = true;
    DeskCompP2.ComponentImpl  _comp = new DeskCompP2.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
